package com.bol.kalaha.model.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void getHouseIndex() {
        assertEquals(7,Player.PLAYER_NORTH.getHouseIndex());
        assertEquals(14,Player.PLAYER_SOUTH.getHouseIndex());
    }
}