package com.bol.kalaha.model.entities;

import com.bol.kalaha.controller.helper.ControllerUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

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
