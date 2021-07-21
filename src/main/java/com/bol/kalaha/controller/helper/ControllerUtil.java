package com.bol.kalaha.controller.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ControllerUtil {

    private final Environment environment;

    @Autowired
    public ControllerUtil(Environment environment) {
        this.environment = environment;
    }

    public String getUrl(final String gameId) {
        final int port = environment.getProperty("server.port", Integer.class, 8080);
        return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(),
                port, gameId);
    }

    public static Long generateRandomNum() {
        return ThreadLocalRandom.current().nextLong(1, 99999_9999 + 1);
    }

}
