package com.bol.kalaha.controller;

import com.bol.kalaha.controller.helper.ControllerUtil;
import com.bol.kalaha.model.GameResponse;
import com.bol.kalaha.model.entities.Game;
import com.bol.kalaha.model.entities.Pit;
import com.bol.kalaha.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GameController {

    private final GameService service;
    private final ControllerUtil controllerUtil;

    @Autowired
    public GameController(final GameService service, ControllerUtil controllerUtil) {
        this.service = service;
        this.controllerUtil = controllerUtil;
    }

    @GetMapping({"", "/", "index", "index.html"})
    public String index() {
        return "index";
    }

    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame() {
        final Game game = service.createGame();
        final GameResponse gameResponse = new GameResponse(game.getId(), controllerUtil.getUrl(game.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(gameResponse);
    }

    @PutMapping("/games/{gameId}/pits/{pitId}")
    public ResponseEntity<GameResponse> playGame(@PathVariable final String gameId,
                                                 @PathVariable final Integer pitId) {
        final Game game = service.play(gameId, pitId);
        final Map<Integer, String> status = new HashMap<>();
        for (Pit pit: game.getBoard().getPits()) {
            status.put(pit.getId(), String.valueOf(pit.getStoneCount()));
        }
        final GameResponse gameResponse = new GameResponse(game.getId(), controllerUtil.getUrl(game.getId()), status);
        return ResponseEntity.status(HttpStatus.OK).body(gameResponse);
    }
}