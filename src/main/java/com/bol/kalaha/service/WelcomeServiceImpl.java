package com.bol.kalaha.service;


import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImpl implements WelcomeService {

    @Override
    public String sayWelcome(String name) {
        return "Hello " + name;
    }
}
