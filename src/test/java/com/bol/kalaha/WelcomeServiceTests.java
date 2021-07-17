package com.bol.kalaha;

import com.bol.kalaha.service.WelcomeService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WelcomeServiceTests {

    @Autowired
    private WelcomeService welcomeService;

    @Test
    void greeting() {
        Assert.assertEquals("Hello Mohsen", welcomeService.sayWelcome("Mohsen"));
    }

}
