package io.nightlyside.enstabretagne.ctfa.entities;

import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.TeamRepository;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
        indexes = {@Index(name = "id_index", columnList = "id", unique = true)})
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

    @Column(name="solved_challenges")
    private String solvedChallenges;

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

    @Override
    public String toString() {
        return "User{" +
                "id = " + id +
                ", username = " + username +
                ", email = " + email +
                ", role = " + role +
                ", teamId = " + teamId +
                ", password = " + password +
                ", challenges_solved = " + solvedChallenges + "}";
    }

    public Integer getScore(ChallengeRepository challengeRepository) {
        if (solvedChallenges == null || solvedChallenges.equals(""))
            return 0;

        int score = 0;
        String[] challIdStrArray = solvedChallenges.split(",");
        for (String challIdStr : challIdStrArray) {
            int challId = Integer.parseInt(challIdStr);
            score += challengeRepository.findById(challId).getScore();
        }
        return score;
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
