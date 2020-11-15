package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.entities.ChallengeSolve;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.TeamRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.DatatypeConverter;
import javax.validation.Valid;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private ChallengeSolveRepository challengeSolveRepository;

    @GetMapping({"/", "", "/index.html", "/index"})
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("teamsRepo", teamRepository);
        model.addAttribute("challRepo", challengeRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
        return "users";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    public @ResponseBody User getUser(@PathVariable Integer id) {
        return userRepository.findById(id).get();
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes attributes) throws NoSuchAlgorithmException {
        User existing_email = userRepository.findByEmail(user.getEmail());
        User existing_username = userRepository.findByUsername(user.getUsername());
        if (existing_email != null){
            result.rejectValue("email", null, "Un compte a déjà été crée avec cette adresse email");
        }
        if (existing_username != null) {
            result.rejectValue("username", null, "Un compte a déjà été crée avec ce nom d'utilisateur");
        }

        if (result.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            attributes.addFlashAttribute("user", user);
            return "redirect:/register";
        }

        // l'utilisateur est légit donc on lui trouve un id
        user.setId(userRepository.getMaxId() + 1);
        // Puis on calcule son mot de passe
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Puis on lui donne son role
        user.setRole("ROLE_USER");

        System.out.println(user.toString());

        userRepository.save(user);
        return "redirect:/register?success";
    }
}
