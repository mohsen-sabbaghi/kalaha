package com.bol.kalaha.model.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerTest {

    @Test
    public void getHouseIndex() {
        assertEquals(7,Player.PLAYER_NORTH.getHouseIndex());
        assertEquals(14,Player.PLAYER_SOUTH.getHouseIndex());
    }
}