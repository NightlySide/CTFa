package io.nightlyside.enstabretagne.ctfa.entities;

import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue
    private Integer id;

    private String teamname;
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
