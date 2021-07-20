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
        for (int i = PIT_START_INDEX; i <=PIT_END_INDEX ; i++) {
            pits.add(new Pit(i));
        }
    }

    public Pit getPit(final int index) {
        return this.pits.get((index - 1) % Board.PIT_END_INDEX);
    }

    public int getStoneCount(final Player player, final boolean includeHouse) {
        return this.getPits().stream()
                .filter(pit -> (pit.getOwner().equals(player) && (includeHouse || !pit.isHouse())))
                .mapToInt(Pit::getStoneCount).sum();
    }
}
