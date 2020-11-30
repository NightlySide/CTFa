package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.UserDetailsImpl;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    ChallengeSolveRepository challengeSolveRepository;
    @Autowired
    ChallengeRepository challengeRepository;
    @Autowired
    ChallengeCategoryRepository challengeCategoryRepository;

    @GetMapping({"/", "", "/index", "/index.html"})
    public String adminPanel(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedUser", loggedUserDetails.getUser());
        return "admin/index";
    }

    @GetMapping("/users")
    public String manageUsers(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("loggedUser", loggedUserDetails.getUser());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("teamRepo", teamRepository);
        model.addAttribute("challRepo", challengeRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
        return "admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        User userToEdit = userRepository.findById(id);
        model.addAttribute("userToEdit", userToEdit);
        model.addAttribute("teams", teamRepository.findAll());

        return "admin/edit-user";
    }

    @GetMapping("/teams")
    public String manageTeams(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("loggedUser", loggedUserDetails.getUser());
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("userRepo", userRepository);
        model.addAttribute("teamRepo", teamRepository);
        model.addAttribute("challRepo", challengeRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
        return "admin/teams";
    }

    @GetMapping("/challenges")
    public String manageChallenges(Model model) {

        model.addAttribute("challenges", challengeRepository.findAll());
        model.addAttribute("challCatRepo", challengeCategoryRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
        return "admin/challenges";
    }
}
