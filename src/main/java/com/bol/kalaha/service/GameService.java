package com.bol.kalaha.service;

import com.bol.kalaha.model.entities.Game;

public interface GameService {

    Game createGame();

    Game play(String gameId, Integer pitId);

}
