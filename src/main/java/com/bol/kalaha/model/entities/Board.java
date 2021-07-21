package com.bol.kalaha.model.entities;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Board {

    public static final int PIT_START_INDEX = 1;
    public static final int PIT_END_INDEX = 14;

    private final List<Pit> pits;

    public Board() {
        pits = new ArrayList<>(PIT_END_INDEX);
        for (int i = PIT_START_INDEX; i <= PIT_END_INDEX; i++) {
            pits.add(new Pit(i));
        }
    }

    public Pit getPit(final int index) {
//        % Board.PIT_END_INDEX in order to make return to first index in case of upper 14 indices
        return this.pits.get((index - 1) % Board.PIT_END_INDEX);
    }
    public int getStoneCount(final Player player, final boolean includeHouse) {
        int sum = 0;
        for (Pit pit : pits) {
            if (pit.getOwner().equals(player) && (includeHouse || !pit.isHouse())) {
                sum += pit.getStoneCount();
            }
        }
        return sum;
    }
}
