package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.entities.ChallengeSolve;
import io.nightlyside.enstabretagne.ctfa.entities.Team;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.TeamRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NavigationController {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChallengeSolveRepository challengeSolveRepository;
    @Autowired
    ChallengeRepository challengeRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "register";
    }

    @GetMapping("/scoreboard")
    public String getScoreBoard(Model model) {
        model.addAttribute("teamRepo", teamRepository);
        model.addAttribute("userRepo", userRepository);
        model.addAttribute("challRepo", challengeRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);

        String[] default_colors = {"#3366CC","#DC3912","#FF9900","#109618","#990099","#3B3EAC","#0099C6","#DD4477","#66AA00","#B82E2E","#316395","#994499","#22AA99","#AAAA11","#6633CC","#E67300","#8B0707","#329262","#5574A6","#3B3EAC"};

        JSONArray teamPoints = new JSONArray();
        int idx = 0;
        for (Team team : teamRepository.findAll()) {
            JSONObject obj = new JSONObject();
            obj.put("label", team.getTeamname());
            obj.put("data", team.getJsonChallengeSolve(userRepository, challengeSolveRepository, challengeRepository));
            obj.put("lineTension", 0);
            obj.put("borderColor", default_colors[idx % default_colors.length]);
            obj.put("backgroundColor", default_colors[idx % default_colors.length]);
            //obj.put("backgroundColor", "rgba(255, 0, 0, 0.5)");
            obj.put("fill", false);

            teamPoints.put(obj);
            idx++;
        }

        //System.out.println(teamPoints.toString());
        model.addAttribute("teamPointsData", teamPoints);

        return "scoreboard";
    }
}
