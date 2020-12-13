package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.UserDetailsImpl;
import io.nightlyside.enstabretagne.ctfa.entities.Challenge;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

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

    @GetMapping("/users/add")
    public String addUser(Model model) {
        if (!model.containsAttribute("userToEdit")) {
            model.addAttribute("userToEdit", new User());
        }
        return "admin/edit-user";
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
    public String manageChallenges(@RequestParam(required = false) String query, Model model) {
        model.addAttribute("query", query);
        if (query == null)
            model.addAttribute("challenges", challengeRepository.findAll());
        else
            model.addAttribute("challenges", challengeRepository.search(query.toLowerCase()));
        model.addAttribute("challCatRepo", challengeCategoryRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
        return "admin/challenges";
    }

    @GetMapping("/challenges/add")
    public String addChallenges(Model model) {
        if (!model.containsAttribute("challenge")) {
            model.addAttribute("challenge", new Challenge());
        }
        model.addAttribute("challCat", challengeCategoryRepository.findAll());
        return "admin/edit-challenge";
    }

    @GetMapping("/challenges/edit/{id}")
    public String editChallenge(@PathVariable int id, Model model) {
        model.addAttribute("challenge", challengeRepository.findById(id));
        model.addAttribute("challCat", challengeCategoryRepository.findAll());

        return "admin/edit-challenge";
    }

    @PostMapping("/challenges/save")
    public String saveChallenge(@Valid @ModelAttribute("challenge") Challenge challenge, BindingResult result, RedirectAttributes attributes) {
        Challenge existing_challenge = challengeRepository.findByTitleLike(challenge.getTitle());
        if (existing_challenge != null && !existing_challenge.getId().equals(challenge.getId())){
            result.rejectValue("title", null, "A challenge already exists with this name");
        }

        if (result.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.challenge", result);
            attributes.addFlashAttribute("challenge", challenge);
            return "redirect:/register";
        }

        if (challenge.getId() == null) {
            // l'utilisateur est légit donc on lui trouve un id
            challenge.setId(challengeRepository.getMaxId() + 1);
        }

        challenge = challengeRepository.save(challenge);
        System.out.println(challenge.toString());
        return "redirect:/admin/challenges/edit/" + challenge.getId() + "?success";
    }
}
