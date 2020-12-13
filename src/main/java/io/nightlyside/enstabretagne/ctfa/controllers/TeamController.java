package io.nightlyside.enstabretagne.ctfa.controllers;

import io.nightlyside.enstabretagne.ctfa.UserDetailsImpl;
import io.nightlyside.enstabretagne.ctfa.entities.ChallengeSolve;
import io.nightlyside.enstabretagne.ctfa.entities.Team;
import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.TeamRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private ChallengeSolveRepository challengeSolveRepository;

    @GetMapping({"/", "", "/index", "/index.html"})
    public String teams(@RequestParam(required = false) String query, Model model) {
        if (query == null)
            model.addAttribute("teams", teamRepository.findAll());
        else
            model.addAttribute("teams", teamRepository.search(query.toLowerCase()));

        model.addAttribute("query", query);
        model.addAttribute("usersRepo", userRepository);
        model.addAttribute("challRepo", challengeRepository);
        model.addAttribute("challSolveRepo", challengeSolveRepository);
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
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = loggedUserDetails.getUser();
        // if the user already has a team
        if (loggedUser.getTeamId() != null)
            return "redirect:/team";

        if (!model.containsAttribute("team"))
            model.addAttribute("team", new Team());
        return "teams/join";
    }

    @GetMapping("/create")
    public String createTeam(Model model) {
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = loggedUserDetails.getUser();
        // if the user already has a team
        if (loggedUser.getTeamId() != null)
            return "redirect:/team";

        if (!model.containsAttribute("team"))
            model.addAttribute("team", new Team());
        return "teams/create";
    }

    @PostMapping("/join")
    public String userJoinTeam(@Valid @ModelAttribute("team") Team team, BindingResult result, RedirectAttributes attributes) {
        Team existingTeam = teamRepository.findByTeamname(team.getTeamname());
        if (existingTeam == null) {
            result.rejectValue("teamname", null, "Aucune équipe n'existe avec ce nom.");
        }
        else {
            // On vérifie que les mots de passe matchent
            // Puis on calcule son mot de passe
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
            boolean matchingPassword = passwordEncoder.matches(team.getPassword(), existingTeam.getPassword());
            if (!matchingPassword)
                result.rejectValue("password", null, "Mauvais mot de passe.");
        }

        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.team", result);
            attributes.addFlashAttribute("team", team);
            return "redirect:/teams/join";
        }

        // on a trouvé l'équipe et le mot de passe correspond
        // on met à jour l'utilisateur
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = loggedUserDetails.getUser();
        loggedUser.setTeamId(existingTeam.getId());
        // on met à jour la BDD
        userRepository.save(loggedUser);

        return "redirect:/team";
    }

    @PostMapping("/create")
    public String saveTeam(@Valid @ModelAttribute("team") Team team, BindingResult result, RedirectAttributes attributes) {
        Team existingTeam = teamRepository.findByTeamname(team.getTeamname());
        if (existingTeam != null){
            result.rejectValue("teamname", null, "Une team a déjà été crée avec ce nom");
        }

        if (result.hasErrors()){
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.team", result);
            attributes.addFlashAttribute("team", team);
            return "redirect:/teams/create";
        }

        // l'équipe est légit donc on lui trouve un id
        team.setId(teamRepository.getMaxId() + 1);
        // Puis on calcule son mot de passe
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        team.setPassword(passwordEncoder.encode(team.getPassword()));

        System.out.println(team.toString());

        //puis on attribue l'équipe à l'utilisateur
        UserDetailsImpl loggedUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = loggedUserDetails.getUser();
        loggedUser.setTeamId(team.getId());

        //on met à jour la BDD
        teamRepository.save(team);
        userRepository.save(loggedUser);
        return "redirect:/team";
    }
}
