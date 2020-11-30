package io.nightlyside.enstabretagne.ctfa.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="challenge_solves")
public class ChallengeSolve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="challenge_id")
    private Integer challengeId;

    private LocalDateTime date;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChallengeId() {
        return challengeId;
    }
    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPrettyDate() {
        return String.format("%02d/%02d/%d - %02d:%02d", date.getDayOfMonth(), date.getMonthValue(), date.getYear(), date.getHour(), date.getMinute());
    }

    public ChallengeSolve() {}
    public ChallengeSolve(Integer id, Integer userId, Integer challengeId, LocalDateTime date) {
        this.id = id;
        this.userId = userId;
        this.challengeId = challengeId;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ChallengeSolve{" +
                "id = " + id +
                ", userId = " + userId +
                ", challengeId = " + challengeId +
                ", date = " + date.toString() + "}";
    }
}
