package com.bol.kalaha.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class GameResponse {

    private final String id;
    private final String uri;
    private final Map<Integer, String> status;

    public GameResponse(String id, String uri) {
        this(id, uri, null);
    }

}
