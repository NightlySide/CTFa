package io.nightlyside.enstabretagne.ctfa.entities;

import javax.persistence.*;

@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="category_id")
    private Integer categoryId;
    private String title;
    private Integer score;
    private String description;
    @Column(name="link_to_file")
    private String linkToFile;
    private String flag;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDescription() {
        return description.replace("\\r\\n", "\n");
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkToFile() {
        return linkToFile;
    }
    public void setLinkToFile(String linkToFile) {
        this.linkToFile = linkToFile;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Challenge(Integer id,
                     Integer categoryId,
                     String title,
                     Integer score,
                     String description,
                     String linkToFile,
                     String flag) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.score = score;
        this.description = description;
        this.linkToFile = linkToFile;
        this.flag = flag;
    }

    public Challenge() {}
}
