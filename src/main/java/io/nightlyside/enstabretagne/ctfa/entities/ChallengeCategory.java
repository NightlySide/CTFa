package io.nightlyside.enstabretagne.ctfa.entities;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class ChallengeCategory {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ChallengeCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public ChallengeCategory() {}
}
