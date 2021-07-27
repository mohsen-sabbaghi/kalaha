package com.bol.kalaha.model.entities;

import lombok.Data;

@Data
public class Pit {

    private final int id;
    private int stoneCount;

    public Pit(final int id) {
        this.id = id;
        if (!isHouse()) {
            setStoneCount(6);
        }
    }

    public boolean isHouse() {
        return (getId() == Player.PLAYER_NORTH.getHouseIndex())
                || (getId() == Player.PLAYER_SOUTH.getHouseIndex());
    }

    public Player getOwner() {
        if (getId() <= Player.PLAYER_NORTH.getHouseIndex()) {
            return Player.PLAYER_NORTH;
        } else {
            return Player.PLAYER_SOUTH;
        }
    }

    public boolean isDistributable(final Player turn) {
        return (!turn.equals(Player.PLAYER_NORTH)
                || (this.getId() != Player.PLAYER_SOUTH.getHouseIndex()))
                && (!turn.equals(Player.PLAYER_SOUTH)
                || (this.getId() != Player.PLAYER_NORTH.getHouseIndex()));
    }
}
