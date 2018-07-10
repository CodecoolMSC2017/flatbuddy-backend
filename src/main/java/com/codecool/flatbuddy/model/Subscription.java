package com.codecool.flatbuddy.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ad_id")
    private Integer adId;
    private Date date;

    public Integer getId() {
        return id;
    }

    public Integer getAdId() {
        return adId;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
