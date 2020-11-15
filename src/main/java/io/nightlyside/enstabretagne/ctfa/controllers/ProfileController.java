package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.UserDetailsImpl;
import io.nightlyside.enstabretagne.ctfa.entities.ChallengeSolve;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.TeamRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    ChallengeRepository challRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChallengeSolveRepository challengeSolveRepository;

    @GetMapping("/team")
    public String getProfileTeam(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = loggedUserDetails.getUser();
        model.addAttribute("user", loggedUser);
        model.addAttribute("userRepo", userRepository);
        model.addAttribute("team", teamRepository.findById(loggedUser.getTeamId()).get());
        model.addAttribute("challRepo", challRepo);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
        return "profile/team";
    }

    @GetMapping("/user")
    public String getProfileUser(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = loggedUserDetails.getUser();
        model.addAttribute("user", loggedUser);
        model.addAttribute("teamRepo", teamRepository);
        model.addAttribute("challRepo", challRepo);
        model.addAttribute("userRepo", userRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
        return "profile/user";
    }
}
