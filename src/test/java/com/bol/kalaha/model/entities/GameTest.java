package com.bol.kalaha.model.entities;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void getId() {
        Game game = new Game();
        System.err.println("game.getId: "+game.getId());
        System.err.println("game.getBoard: "+game.getBoard());
        System.err.println("game.getTurn: "+game.getTurn());
        System.err.println("game.getWinner: "+game.getWinner());

        assertNotNull(game.getId());
        assertNotNull(game.getBoard());

        assertNull(game.getTurn());
        assertNull(game.getWinner());
    }

    @Test
    public void getWinner() {
        final Game game = new Game();

        game.setWinner(Player.PLAYER_NORTH);
        assertEquals(Player.PLAYER_NORTH, game.getWinner());

        game.setWinner(Player.PLAYER_SOUTH);
        assertEquals(Player.PLAYER_SOUTH, game.getWinner());
    }

    @Test
    public void getTurn() {
        final Game game = new Game();

        game.setTurn(Player.PLAYER_NORTH);
        assertEquals(Player.PLAYER_NORTH, game.getTurn());

        game.setTurn(Player.PLAYER_SOUTH);
        assertEquals(Player.PLAYER_SOUTH, game.getTurn());
    }
}