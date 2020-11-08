package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.entities.Team;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.TeamRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeRepository challengeRepository;

    @GetMapping({"/", "", "/index", "/index.html"})
    public String teams(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("usersRepo", userRepository);
        model.addAttribute("challRepo", challengeRepository);
        return "teams/list";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @GetMapping("{id}")
    public @ResponseBody Team getTeam(@PathVariable Integer id) {
        return teamRepository.findById(id).get();
    }

    @GetMapping("/join")
    public String joinTeam(Model model) {
        return "teams/join";
    }

    @GetMapping("/create")
    public String createTeam(Model model) {
        return "teams/create";
    }
}
