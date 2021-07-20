package com.bol.kalaha.model.entities;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class PitTest {

    @Test
    public void getStoneCount() {
        final Pit pit01 = new Pit(1);
        assertEquals(6, pit01.getStoneCount());
        final Pit pit07 = new Pit(7);
        assertEquals(0, pit07.getStoneCount());
    }

    @Test
    public void isHouse() {
        final Pit pit07 = new Pit(7);
        assertTrue(pit07.isHouse());
        final Pit pit14 = new Pit(14);
        assertTrue(pit14.isHouse());

        final Pit pit4 = new Pit(4);
        assertFalse(pit4.isHouse());

        final Pit pitRnd = new Pit(new Random().nextInt(14 + 1));
        assertFalse(pitRnd.isHouse());
    }

    @Test
    public void getOwner() {
        final Pit pit01 = new Pit(1);
        assertSame(Player.PLAYER_NORTH, pit01.getOwner());
        final Pit pit07 = new Pit(7);
        assertSame(Player.PLAYER_NORTH, pit07.getOwner());

        final Pit pit08 = new Pit(8);
        assertSame(Player.PLAYER_SOUTH, pit08.getOwner());
        final Pit pit14 = new Pit(14);
        assertSame(Player.PLAYER_SOUTH, pit14.getOwner());

    }

    @Test
    public void isDistributable() {
//        for (int i = 1; i <= 14; i++) {
//            System.err.println("Pit #" + i + " isDistributable for PLAYER_NORTH: " + new Pit(i).isDistributable(Player.PLAYER_NORTH));
//            System.err.println("Pit #" + i + " isDistributable for PLAYER_SOUTH: " + new Pit(i).isDistributable(Player.PLAYER_SOUTH));
//        }
        final Pit pit01 = new Pit(1);
        assertTrue(pit01.isDistributable(Player.PLAYER_NORTH));

        final Pit pit07 = new Pit(7);
        assertFalse(pit07.isDistributable(Player.PLAYER_SOUTH));

        final Pit pit10 = new Pit(10);
        assertTrue(pit10.isDistributable(Player.PLAYER_SOUTH));

        final Pit pit14 = new Pit(14);
        assertTrue(pit14.isDistributable(Player.PLAYER_SOUTH));
        assertFalse(pit14.isDistributable(Player.PLAYER_NORTH));
    }

    @Test
    public void setStoneCount() {
        final Pit pit01 = new Pit(1);
        pit01.setStoneCount(10);
        assertEquals(10, pit01.getStoneCount());
    }
}