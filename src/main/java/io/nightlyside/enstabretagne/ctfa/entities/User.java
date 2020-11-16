package io.nightlyside.enstabretagne.ctfa.entities;

import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.TeamRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
        indexes = {@Index(name = "id_index", columnList = "id", unique = true)})
public class User {

    @Id
    private Integer id;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min=2, max=40, message = "Le nom d'utilisateur doit être compris entre 2 et 40 caractères")
    private String username;

    @NotBlank(message = "Une adresse email est obligatoire")
    @Email
    private String email;

    @NotBlank(message = "Un mot de passe est obligatoire")
    @Size(min=6, message = "Le mot de passe doit faire minimum 6 caractères")
    private String password;

    private String role;

    @Column(name="team_id")
    private Integer teamId;

    public Integer getId() { return id; }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public Integer getTeamId() {
        return teamId;
    }
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public boolean isInATeam() {
        return teamId != null;
    }

    public Integer getSolvedChallengesCount(ChallengeSolveRepository challengeSolveRepository) {
        return challengeSolveRepository.countChallengeSolveByUserIdEquals(this.getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id = " + id +
                ", username = " + username +
                ", email = " + email +
                ", role = " + role +
                ", teamId = " + teamId +
                ", password = " + password + "}";
    }

    public Integer getScore(ChallengeSolveRepository challengeSolveRepository, ChallengeRepository challengeRepository) {
        if (this.getSolvedChallengesCount(challengeSolveRepository) == 0)
            return 0;

        int score = 0;
        for (ChallengeSolve challengeSolve : challengeSolveRepository.findAllByUserIdEquals(this.getId())) {
            if (challengeRepository.findById(challengeSolve.getChallengeId()).isPresent())
                score += challengeRepository.findById(challengeSolve.getChallengeId()).get().getScore();
            else
                System.out.println(String.format("Challenge not found for user : %s and Challenge id : %d", getUsername(), challengeSolve.getChallengeId()));
        }
        return score;
    }

    public Integer getRank(UserRepository userRepository, ChallengeSolveRepository challengeSolveRepository, ChallengeRepository challengeRepository) {
        if (this.getSolvedChallengesCount(challengeSolveRepository) == 0)
            return 0;

        int userScore = getScore(challengeSolveRepository, challengeRepository);
        int rank = 1;
        for (User u : userRepository.findAll()) {
            if (!u.getId().equals(this.getId())) {
                if (u.getScore(challengeSolveRepository, challengeRepository) > userScore) {
                    rank++;
                }
            }
        }
        return rank;
    }

    public String getRankPresentation(UserRepository userRepository, ChallengeSolveRepository challengeSolveRepository, ChallengeRepository challengeRepository) {
        String rank = String.valueOf(this.getRank(userRepository, challengeSolveRepository, challengeRepository));
        char lastChar = rank.charAt(rank.length() - 1);
        if (rank.length() == 1) {
            switch (rank) {
                case "1": return rank + "st";
                case "2": return rank + "nd";
                case "3": return rank + "rd";
                default: return rank + "th";
            }
        }
        else {
            char beforeLastChar = rank.charAt(rank.length() - 2);
            if (lastChar == '1' && beforeLastChar != '1')
                return rank + "st";
            else if (lastChar == '2' && beforeLastChar != '1')
                return rank + "nd";
            else if (lastChar == '3' && beforeLastChar != '1')
                return rank + "rd";
            else
                return rank + "th";
        }
    }

    public String getTeamName(TeamRepository teamRepository) {
        if (teamId == null)
            return "";

        return teamRepository.findById(teamId).get().getTeamname();
    }

    public User(Integer id, String username, String email, String password, Integer teamId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.teamId = teamId;
    }
    public User() { }
}
