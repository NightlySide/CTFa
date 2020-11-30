package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.UserDetailsImpl;
import io.nightlyside.enstabretagne.ctfa.entities.Challenge;
import io.nightlyside.enstabretagne.ctfa.entities.ChallengeCategory;
import io.nightlyside.enstabretagne.ctfa.entities.ChallengeSolve;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeCategoryRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    public ChallengeRepository challengeRepository;
    @Autowired
    public ChallengeCategoryRepository challengeCategoryRepository;
    @Autowired
    public ChallengeSolveRepository challengeSolveRepository;

    @GetMapping({"/", "", "/index", "/index.html"})
    public String challenges(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("loggedUser", loggedUserDetails.getUser());
        model.addAttribute("challRepo", challengeRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
        model.addAttribute("categories", challengeCategoryRepository.findAll());
        return "challenges";
    }

    @GetMapping("{id}")
    public @ResponseBody
    Challenge getChallenge(@PathVariable Integer id) {
        return challengeRepository.findById(id).get();
    }

    @GetMapping("/check/{id}")
    public @ResponseBody
    Map<String, Object> checkFlag(@PathVariable Integer id, @RequestParam(required = true, value="flag") String flag) {
        Map<String, Object> response = new HashMap<>();
        if (challengeRepository.findById(id).isEmpty()) {
            response.put("correct", false);
            response.put("reason", "The challenge wasn't found with the id: " + String.valueOf(id));
        }
        else {
            Challenge chall = challengeRepository.findById(id).get();

            if (chall.getFlag().equals(flag)) {
                // on récupère l'utilisateur
                UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                // on récupère ses solves
                List<ChallengeSolve> solve = challengeSolveRepository.findAllByUserIdEquals(loggedUserDetails.getUser().getId());
                boolean alreadyFlagged = false;
                for (ChallengeSolve s : solve) {
                    // si l'utilisateur à déjà flag le challenge
                    if (s.getChallengeId().equals(id)) {
                        alreadyFlagged = true;
                        break;
                    }
                }

                response.put("correct", true);
                if (alreadyFlagged) {
                    response.put("reason", "You already flagged that challenge... No more points for you!");
                } else {
                    // on save le nouveau solve
                    ChallengeSolve challSolve = new ChallengeSolve();
                    challSolve.setUserId(loggedUserDetails.getUser().getId());
                    challSolve.setChallengeId(chall.getId());
                    challSolve.setDate(LocalDateTime.now());

                    challengeSolveRepository.save(challSolve);

                    // on prépare la réponse à envoyer
                    response.put("reason", "You flagged that challenge! You win " + String.valueOf(chall.getScore()) + "pts.");
                }
            } else {
                response.put("correct", false);
                response.put("reason", "Wrong flag try again!");
            }
        }

        return response;
    }
}
