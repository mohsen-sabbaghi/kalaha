package com.bol.kalaha.model.entities;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardTest {

    @Test
    public void testInitialization() {
        final Board givenBoard = new Board();

        Assert.assertNotNull(givenBoard.getPits());
        Assert.assertEquals(Board.PIT_END_INDEX, givenBoard.getPits().size());
    }

    @Test
    public void testGetPit() {
        final Board givenBoard = new Board();
        final Pit givenPit = givenBoard.getPit(4);
        final Pit givenPit2 = givenBoard.getPit(13);

        for (int i = 1; i <=14 ; i++) {
            System.err.println(givenBoard.getPit(i));
        }

        Assert.assertNotNull(givenPit);
        Assert.assertEquals(4, givenPit.getId());

        Assert.assertNotNull(givenPit2);
        Assert.assertEquals(13, givenPit2.getId());
    }

    @Test
    public void getStoneCount() {

        final Board board1 = new Board();
        Assert.assertEquals(36, board1.getStoneCount(Player.PLAYER_NORTH, true));

        final Board board2 = new Board();
        System.out.println(board2);

        board2.getPit(5).setStoneCount(0);
        System.out.println(board2);
        Assert.assertEquals(30, board2.getStoneCount(Player.PLAYER_NORTH, true));

        board2.getPit(11).setStoneCount(9);
        System.out.println(board2);
        Assert.assertEquals(39, board2.getStoneCount(Player.PLAYER_SOUTH, true));

        board2.getPit(14).setStoneCount(10);
        System.out.println(board2);
        Assert.assertEquals(49, board2.getStoneCount(Player.PLAYER_SOUTH, true));
        Assert.assertEquals(39, board2.getStoneCount(Player.PLAYER_SOUTH, false));
    }
}