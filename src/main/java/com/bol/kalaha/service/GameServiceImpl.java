package com.bol.kalaha.service;

import com.bol.kalaha.model.entities.Board;
import com.bol.kalaha.model.entities.Game;
import com.bol.kalaha.model.entities.Pit;
import com.bol.kalaha.model.entities.Player;
import com.bol.kalaha.model.exceptions.GameNotFoundException;
import com.bol.kalaha.model.exceptions.IllegalMoveException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private Game CURRENT_GAME;

    @Override
    public Game createGame() {
        return CURRENT_GAME = new Game();
    }

    @Override
    public Game play(String gameId, Integer pitId) {
        if (CURRENT_GAME == null) {
            throw new GameNotFoundException(gameId);
        }
        distributeStones(CURRENT_GAME, pitId);
        checkGameOver(CURRENT_GAME);
        return CURRENT_GAME;
    }

    private void distributeStones(final Game game, int pitId) {
        final Pit startPit = game.getBoard().getPit(pitId);
        validateMove(game, pitId);
        int stoneToDistribute = startPit.getStoneCount();
        startPit.setStoneCount(0);
        while (stoneToDistribute > 0) {
            final Pit currentPit = game.getBoard().getPit(++pitId);
            if (currentPit.isDistributable(game.getTurn())) {
                currentPit.setStoneCount(currentPit.getStoneCount() + 1);
                stoneToDistribute--;
            }
        }
        lastEmptyPit(game, pitId);
        decidePlayerTurn(game, pitId);
    }

    private void checkGameOver(final Game game) {
        final int northPitStoneCount = game.getBoard().getStoneCount(Player.PLAYER_NORTH, false);
        final int southPitStoneCount = game.getBoard().getStoneCount(Player.PLAYER_SOUTH, false);
        if ((northPitStoneCount == 0) || (southPitStoneCount == 0)) {
            final Pit houseNorth = game.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex());
            final Pit houseSouth = game.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex());
            houseNorth.setStoneCount(houseNorth.getStoneCount() + northPitStoneCount);
            houseSouth.setStoneCount(houseSouth.getStoneCount() + southPitStoneCount);
            determineWinner(game);
            resetBoard(game);
        }
    }

    private void determineWinner(final Game game) {
        final int houseNorthStoneCount = game.getBoard().getStoneCount(Player.PLAYER_NORTH, true);
        final int houseSouthStoneCount = game.getBoard().getStoneCount(Player.PLAYER_SOUTH, true);
        if (houseNorthStoneCount > houseSouthStoneCount) {
            game.setWinner(Player.PLAYER_NORTH);
        } else if (houseNorthStoneCount < houseSouthStoneCount) {
            game.setWinner(Player.PLAYER_SOUTH);
        }
    }

    private void lastEmptyPit(final Game game, final int endPitId) {
        final Pit endPit = game.getBoard().getPit(endPitId);
        if (!endPit.isHouse() && endPit.getOwner().equals(game.getTurn()) && (endPit.getStoneCount() == 1)) {
            final Pit oppositePit = game.getBoard().getPit(Board.PIT_END_INDEX - endPit.getId());
            if (oppositePit.getStoneCount() > 0) {
                final Pit house = game.getBoard().getPit(endPit.getOwner().getHouseIndex());
                house.setStoneCount(
                        (house.getStoneCount() + oppositePit.getStoneCount()) + endPit.getStoneCount());
                oppositePit.setStoneCount(0);
                endPit.setStoneCount(0);
            }
        }
    }

    private void decidePlayerTurn(final Game game, final int pitId) {
        final Pit pit = game.getBoard().getPit(pitId);
        if (pit.isHouse() && Player.PLAYER_NORTH.equals(pit.getOwner())
                && Player.PLAYER_NORTH.equals(game.getTurn())) {
            game.setTurn(Player.PLAYER_NORTH);
        } else if (pit.isHouse() && Player.PLAYER_SOUTH.equals(pit.getOwner())
                && Player.PLAYER_SOUTH.equals(game.getTurn())) {
            game.setTurn(Player.PLAYER_SOUTH);
        } else {
            if (Player.PLAYER_NORTH.equals(game.getTurn())) {
                game.setTurn(Player.PLAYER_SOUTH);
            } else {
                game.setTurn(Player.PLAYER_NORTH);
            }
        }
    }

    private void validateMove(final Game game, final int startPitId) {
        final Pit startPit = game.getBoard().getPit(startPitId);
        if (startPit.isHouse()) {
            throw new IllegalMoveException("Can not start from house");
        }
        if (Player.PLAYER_NORTH.equals(game.getTurn())
                && !Player.PLAYER_NORTH.equals(startPit.getOwner())) {
            throw new IllegalMoveException("It's Player North's turn");
        }
        if (Player.PLAYER_SOUTH.equals(game.getTurn())
                && !Player.PLAYER_SOUTH.equals(startPit.getOwner())) {
            throw new IllegalMoveException("It's Player South's turn");
        }
        if (startPit.getStoneCount() == 0) {
            throw new IllegalMoveException("Can not start from empty pit");
        }
        if (game.getTurn() == null) {
            if (Player.PLAYER_NORTH.equals(startPit.getOwner())) {
                game.setTurn(Player.PLAYER_NORTH);
            } else {
                game.setTurn(Player.PLAYER_SOUTH);
            }
        }
    }

    private void resetBoard(final Game game) {
        for (Pit pit : game.getBoard().getPits()) {
            if (!pit.isHouse()) {
                pit.setStoneCount(0);
            }
        }
    }
}
