package io.nightlyside.enstabretagne.ctfa.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer team_id;

    public Integer getId() {
        return id;
    }

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

    public Integer getTeamId() {
        return team_id;
    }

    public void setTeamId(Integer team_id) {
        this.team_id = team_id;
    }

    public User(Integer id, String username, String email, String password, Integer team_id) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.team_id = team_id;
    }

    public User() { }
}
