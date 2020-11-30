package io.nightlyside.enstabretagne.ctfa.entities;

import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeCategoryRepository;
import io.nightlyside.enstabretagne.ctfa.repositories.ChallengeSolveRepository;

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
    private boolean visible;

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

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public String getCategoryName(ChallengeCategoryRepository challCatRepo) {
        ChallengeCategory challCat = challCatRepo.findById(this.getCategoryId()).get();
        return challCat.getName();
    }

    public Integer getNumberOfSolves(ChallengeSolveRepository challengeSolveRepository) {
        return challengeSolveRepository.countChallengeSolveByChallengeIdEquals(this.getId());
    }

    public Challenge(Integer id,
                     Integer categoryId,
                     String title,
                     Integer score,
                     String description,
                     String linkToFile,
                     String flag,
                     boolean visible) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.score = score;
        this.description = description;
        this.linkToFile = linkToFile;
        this.flag = flag;
        this.visible = visible;
    }

    public Challenge() {}
}
