package io.nightlyside.enstabretagne.ctfa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NavigationController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping({"/users", "/users.html"})
    public String users() {
        return "users";
    }

    @GetMapping("/teams/{id}")
    public String showTeam(@PathVariable Long id) {
        return "show_team";
    }

    @GetMapping({"/teams", "/teams.html"})
    public String teams() {
        return "teams";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
