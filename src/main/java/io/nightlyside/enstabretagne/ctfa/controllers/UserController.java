package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    public @ResponseBody User getUser(@PathVariable Integer id) {
        return userRepository.findById(id).get();
    }
}
