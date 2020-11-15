package io.nightlyside.enstabretagne.ctfa.entities;

import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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
        for (User user : userRepository.findByTeamIdEquals(id)) {
            score += user.getScore(challengeSolveRepository, challengeRepository);
        }
        return score;
    }

    public List<User> getTeamMembers(UserRepository userRepository) {
        return userRepository.findByTeamIdEquals(this.getId());
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
