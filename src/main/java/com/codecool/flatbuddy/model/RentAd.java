package com.codecool.flatbuddy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Check;

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
    @ManyToOne
    @JsonManagedReference
    private User user;
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
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "is_premium")
    private boolean isPremium;

    @OneToMany
    @JoinColumn(name="ad_id",referencedColumnName = "id")
    private List<AdPicture> adPictures = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="ad_id", referencedColumnName = "id")
    private List<Subscription> adSubscriptions = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "rentad_id", referencedColumnName = "id")
    private List<RentSlot> slots = new ArrayList<>();

    public RentAd() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<RentSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<RentSlot> slots) {
        this.slots = slots;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }
}
