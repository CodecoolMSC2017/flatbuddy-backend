package com.codecool.flatbuddy.model;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "user_a")
    private Integer userA;
    @Column(name = "user_b")
    private Integer userB;


    @Column(name = "is_matched")
    private boolean isMatched;

    public Match() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserA() {
        return userA;
    }

    public void setUserA(Integer userA) {
        this.userA = userA;
    }

    public Integer getUserB() {
        return userB;
    }

    public void setUserB(Integer userB) {
        this.userB = userB;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }


}
