package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.UserDetailsImpl;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/team")
    public String getProfileTeam(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = loggedUserDetails.getUser();
        model.addAttribute("user", loggedUser);
        return "profile/team";
    }

    @GetMapping("/user")
    public String getProfileUser(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = loggedUserDetails.getUser();
        model.addAttribute("user", loggedUser);
        return "profile/user";
    }
}
