package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.entities.Challenge;
import io.nightlyside.enstabretagne.ctfa.entities.ChallengeCategory;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeCategoryRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    public ChallengeRepository challengeRepository;
    @Autowired
    public ChallengeCategoryRepository challengeCategoryRepository;

    @GetMapping({"/", "", "/index", "/index.html"})
    public String challenges(Model model) {
        model.addAttribute("challRepo", challengeRepository);
        model.addAttribute("categories", challengeCategoryRepository.findAll());
        return "challenges";
    }

    @GetMapping("{id}")
    public @ResponseBody
    Challenge getChallenge(@PathVariable Integer id) {
        return challengeRepository.findById(id).get();
    }
}
