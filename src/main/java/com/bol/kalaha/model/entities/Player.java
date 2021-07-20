package com.bol.kalaha.model.entities;

public enum Player {

    PLAYER_NORTH(Board.PIT_END_INDEX / 2),
    PLAYER_SOUTH(Board.PIT_END_INDEX);

    private final int houseIndex;

    Player(final int houseIndex) {
        this.houseIndex = houseIndex;
    }

    public int getHouseIndex() {
        return this.houseIndex;
    }
}
