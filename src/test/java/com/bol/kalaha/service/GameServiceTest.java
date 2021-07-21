package com.bol.kalaha.service;


import com.bol.kalaha.model.entities.Game;
import com.bol.kalaha.model.entities.Player;
import com.bol.kalaha.model.exceptions.IllegalMoveException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService service;

    private Game gameInitial;
    private Game gameFinishedWinnerSouth;
    private Game gameFinishedWinnerNorth;
    private Game gameNorthMovedFirst;
    private Game gameSouthMovedFirst;
    private Game gameTurnNorth;
    private Game gameTurnSouth;

    @Before
    public void init()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        gameInitial = new Game();
        gameFinishedWinnerSouth = new Game();
        gameFinishedWinnerNorth = new Game();
        gameNorthMovedFirst = new Game();
        gameSouthMovedFirst = new Game();
        gameTurnNorth = new Game();
        gameTurnSouth = new Game();

        final Method resetBoard =
                openMethodForTest(service.getClass().getDeclaredMethod("resetBoard", Game.class));
        final Method distributeStones = openMethodForTest(
                service.getClass().getDeclaredMethod("distributeStones", Game.class, int.class));

        resetBoard.invoke(service, gameFinishedWinnerSouth);
        gameFinishedWinnerSouth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex())
                .setStoneCount(10);
        gameFinishedWinnerSouth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex())
                .setStoneCount(62);
        resetBoard.invoke(service, gameFinishedWinnerNorth);
        gameFinishedWinnerNorth.getBoard().getPit(1).setStoneCount(1);
        gameFinishedWinnerNorth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex())
                .setStoneCount(39);
        gameFinishedWinnerNorth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex())
                .setStoneCount(32);

        distributeStones.invoke(service, gameNorthMovedFirst, 1);
        distributeStones.invoke(service, gameSouthMovedFirst, 10);
        distributeStones.invoke(service, gameTurnNorth, 1);
        distributeStones.invoke(service, gameTurnSouth, 8);
    }

    @Test
    @DirtiesContext
    public void testCreateGame() {
        final Game game = service.createGame();
        System.err.println("game.getId() " + game.getId());
        System.err.println("game: " + game.toString());
        Assert.assertNotNull(game);
    }

    @Test
    @DirtiesContext
    public void testPlay() {
        final Game game = service.createGame();
        service.play(game.getId(), 6);

        Assert.assertEquals(Player.PLAYER_SOUTH, game.getTurn());
        Assert.assertNull(game.getWinner());
        Assert.assertEquals(31, game.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
    }

    @Test
    public void testDecidePlayerTurn() {
        Assert.assertNull(gameInitial.getTurn());
        Assert.assertEquals(Player.PLAYER_NORTH, gameNorthMovedFirst.getTurn());
        Assert.assertEquals(Player.PLAYER_NORTH, gameSouthMovedFirst.getTurn());
    }

    @Test
    public void testDetermineWinner()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method determineWinner =
                openMethodForTest(service.getClass().getDeclaredMethod("determineWinner", Game.class));
        determineWinner.invoke(service, gameInitial);
        determineWinner.invoke(service, gameFinishedWinnerSouth);
        determineWinner.invoke(service, gameFinishedWinnerNorth);

        Assert.assertNull(gameInitial.getWinner());
        Assert.assertEquals(Player.PLAYER_SOUTH, gameFinishedWinnerSouth.getWinner());
        Assert.assertEquals(Player.PLAYER_NORTH, gameFinishedWinnerNorth.getWinner());
    }

    @Test
    public void testCheckGameOver()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method checkGameOver =
                openMethodForTest(service.getClass().getDeclaredMethod("checkGameOver", Game.class));
        checkGameOver.invoke(service, gameInitial);
        checkGameOver.invoke(service, gameFinishedWinnerSouth);
        checkGameOver.invoke(service, gameFinishedWinnerNorth);

        Assert.assertEquals(36, gameInitial.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(36, gameInitial.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
        Assert.assertEquals(10,
                gameFinishedWinnerSouth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(62,

                gameFinishedWinnerSouth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
        Assert.assertEquals(40,
                gameFinishedWinnerNorth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(32,
                gameFinishedWinnerNorth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMovePlayerNorthTurn() throws Throwable {
        try {
            final Method validateMove = openMethodForTest(
                    service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
            validateMove.invoke(service, gameTurnNorth, 12);
        } catch (final Exception e) {
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            } else {
                throw e;
            }
        }
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMovePlayerSouthTurn() throws Throwable {
        try {
            final Method validateMove = openMethodForTest(
                    service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
            validateMove.invoke(service, gameTurnSouth, 1);
        } catch (final Exception e) {
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            } else {
                throw e;
            }
        }
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMoveStartFromEmptyPit() throws Throwable {
        try {
            final Method validateMove = openMethodForTest(
                    service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
            validateMove.invoke(service, gameTurnNorth, 1);
        } catch (final Exception e) {
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            } else {
                throw e;
            }
        }
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMoveStartFromHouse() throws Throwable {
        try {
            final Method validateMove = openMethodForTest(
                    service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
            validateMove.invoke(service, gameInitial, Player.PLAYER_NORTH.getHouseIndex());
        } catch (final Exception e) {
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            } else {
                throw e;
            }
        }
    }

    @Test
    public void testLastEmptyPit()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method resetBoard =
                openMethodForTest(service.getClass().getDeclaredMethod("resetBoard", Game.class));
        final Method lastEmptyPit = openMethodForTest(
                service.getClass().getDeclaredMethod("lastEmptyPit", Game.class, int.class));

        resetBoard.invoke(service, gameTurnNorth);
        gameTurnNorth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex()).setStoneCount(0);
        gameTurnNorth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex()).setStoneCount(0);
        gameTurnNorth.getBoard().getPit(4).setStoneCount(1);
        gameTurnNorth.getBoard().getPit(10).setStoneCount(6);

        lastEmptyPit.invoke(service, gameTurnNorth, 4);

        Assert.assertEquals(7, gameTurnNorth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(0, gameTurnNorth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
    }

    @Test
    public void testValidMovePlayerNorth()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method validateMove = openMethodForTest(
                service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
        validateMove.invoke(service, gameInitial, 1);
        Assert.assertEquals(Player.PLAYER_NORTH, gameInitial.getTurn());
    }

    @Test
    public void testValidMovePlayerSouth()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        final Method validateMove = openMethodForTest(
                service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
        validateMove.invoke(service, gameInitial, 13);
        Assert.assertEquals(Player.PLAYER_SOUTH, gameInitial.getTurn());
    }

    private Method openMethodForTest(final Method method) {
        ReflectionUtils.makeAccessible(method);
        return method;
    }
}
