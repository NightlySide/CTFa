package io.nightlyside.enstabretagne.ctfa.entities;

import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.TeamRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    private Integer id;

    @NotBlank(message="Un nom d'équipe est obligatoire")
    @Size(min=2, max=40, message = "Le nom de team doit être compris entre 2 et 40 caractères")
    private String teamname;
    @NotBlank(message = "Un mot de passe est obligatoire")
    @Size(min=6, message = "Le mot de passe doit faire minimum 6 caractères")
    private String password;
    @Column(name="open_to_register")
    private boolean openToRegister;

    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTeamname() { return teamname; }
    public void setTeamname(String teamname) { this.teamname = teamname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isOpenToRegister() { return openToRegister; }
    public void setOpenToRegister(boolean openToRegister) { this.openToRegister = openToRegister; }

    public Integer getScore(UserRepository userRepository, ChallengeSolveRepository challengeSolveRepository, ChallengeRepository challengeRepository) {
        int score = 0;
        for (User user : this.getTeamMembers(userRepository)) {
            score += user.getScore(challengeSolveRepository, challengeRepository);
        }
        return score;
    }

    public Integer getRank(TeamRepository teamRepository, UserRepository userRepository, ChallengeSolveRepository challengeSolveRepository, ChallengeRepository challengeRepository) {
        int teamScore = getScore(userRepository, challengeSolveRepository, challengeRepository);
        int rank = 1;
        for (Team t : teamRepository.findAll()) {
            if (!t.getId().equals(this.getId())) {
                if (t.getScore(userRepository, challengeSolveRepository, challengeRepository) > teamScore) {
                    rank++;
                }
            }
        }
        return rank;
    }

    public JSONArray getJsonChallengeSolve(UserRepository userRepository, ChallengeSolveRepository challengeSolveRepository, ChallengeRepository challengeRepository) {
        int incrementalScore = 0;
        JSONArray points = new JSONArray();

        List<ChallengeSolve> solves = challengeSolveRepository.findAllByUserIdIn(this.getTeamMembersId(userRepository));
        solves.sort((s1, s2) -> {
            if (s1.getDate().isBefore(s2.getDate())) return -1;
            if (s1.getDate().isAfter(s2.getDate())) return 1;
            return 0;
        });

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

        for (ChallengeSolve solve : solves) {
            Challenge chall = challengeRepository.findById(solve.getChallengeId()).get();
            incrementalScore += chall.getScore();
            JSONObject obj = new JSONObject();
            obj.put("t", format.format(solve.getDate()));
            obj.put("x", chall.getTitle());
            obj.put("y", incrementalScore);
            points.put(obj);
        }

        return points;
    }

    public List<User> getTeamMembers(UserRepository userRepository) {
        return userRepository.findByTeamIdEquals(this.getId());
    }

    public List<Integer> getTeamMembersId(UserRepository userRepository) {
        List<Integer> ids = new ArrayList<>();
        for (User user : this.getTeamMembers(userRepository))
            ids.add(user.getId());
        return ids;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id = " + id +
                ", teamname = " + teamname +
                ", password = " + password +
                ", openToRegister = " + openToRegister + "}";
    }

    public Team(int id, String teamname, String password, boolean openToRegister) {
        this.id = id;
        this.teamname = teamname;
        this.password = password;
        this.openToRegister = openToRegister;
    }
    public Team() {}
}
