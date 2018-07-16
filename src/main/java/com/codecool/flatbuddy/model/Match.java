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
    @Column(name = "is_enabled")
    private boolean isEnabled;


    private Integer status;

    public Match() {
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
