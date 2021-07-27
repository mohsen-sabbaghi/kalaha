package com.bol.kalaha.model.entities;

import com.bol.kalaha.controller.helper.ControllerUtil;
import lombok.Data;

@Data
public class Game {

    private final String id;
    private final Board board;
    private Player winner;
    private Player turn;

    public Game() {
        this.id = ControllerUtil.generateRandomNum().toString();
        this.board = new Board();
    }


}
