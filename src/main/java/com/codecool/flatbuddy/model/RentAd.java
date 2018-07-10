package com.codecool.flatbuddy.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="rent_ads")
public class RentAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    private String country;
    private String state;
    private String city;
    @Column(name = "zip_code")
    private String zipCode;
    private String district;
    private String street;
    private String description;
    private Integer cost;
    private Integer size;
    private String type;
    @Column(name = "is_furnitured")
    private boolean isFurnitured;
    @Column(name = "rooms_available")
    private Integer roomsAvailable;
    @Column(name = "published_date")
    private Date publishedDate;
    @Column(name = "expiration_date")
    private Date expirationDate;
    @OneToMany
    @JoinColumn(name="ad_id",referencedColumnName = "id")
    private List<AdPicture> adPictures = new ArrayList<>();
    @JoinColumn(name="ad_id", referencedColumnName = "id")
    private List<Subscription> adSubscriptions = new ArrayList<>();



    public RentAd() {
    }


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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFurnitured() {
        return isFurnitured;
    }

    public void setFurnitured(boolean furnitured) {
        isFurnitured = furnitured;
    }

    public Integer getRoomsAvailable() {
        return roomsAvailable;
    }

    public void setRoomsAvailable(Integer roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<AdPicture> getAdPictures() {
        return adPictures;
    }

    public List<Subscription> getAdSubscriptions() {
        return adSubscriptions;
    }

    public void setAdPictures(List<AdPicture> adPictures) {
        this.adPictures = adPictures;
    }

    public void setAdSubscriptions(List<Subscription> adSubscriptions) {
        this.adSubscriptions = adSubscriptions;
    }
}
