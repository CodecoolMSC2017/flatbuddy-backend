package com.codecool.flatbuddy.model;

import javax.persistence.*;

@Entity
@Table(name="user_pictures")
public class UserPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="user_id")
    private Integer userId;
    private String path;

    public UserPicture() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPath() {
        return path;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
