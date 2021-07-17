package com.bol.kalaha.controller;

import com.bol.kalaha.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    private final WelcomeService service;

    @Autowired
    public WelcomeController(final WelcomeService service) {
        this.service = service;
    }

    @GetMapping({"", "/", "index", "index.html"})
    public String index() {
        return "index";
    }


    @GetMapping({"welcome", "/welcome", "welcome.html"})
    public String welcomeService(Model model) {
        System.out.println("model = " + model);
        String myMsg = service.sayWelcome("Mohsen");
        System.err.println("message = " + myMsg);

        model.addAttribute("msg", myMsg);
        return "welcome/index";
    }


}
