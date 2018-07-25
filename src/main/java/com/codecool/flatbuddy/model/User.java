package com.codecool.flatbuddy.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name= "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "username")
    private String email;
    @Column(name = "is_flatmate")
    private Boolean isFlatmate;
    private Integer age;
    private String gender;
    private String description;
    private String destination;
    private Boolean enabled;


    @OneToMany
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private List<RentAd> rentAds = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "senderId", cascade = CascadeType.ALL)
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiverId", cascade = CascadeType.ALL)
    private List<Message> receivedMessages = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<UserPicture> pictures = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_a", referencedColumnName = "id")
    private List<Match> matches = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "authorities",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
    )
    @Column(name = "authority")
    private List<String> authorities;

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isFlatmate() {
        return isFlatmate;
    }

    public void setFlatmate(Boolean flatmate) {
        if (firstName == null || lastName == null || age == null || description == null || destination == null) {
            throw new IllegalArgumentException("First name, last name, age, description, destination fields must be filled out to use this feature!");
        }
        isFlatmate = flatmate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<RentAd> getRentAds() {
        return rentAds;
    }

    public void setRentAds(List<RentAd> rentAds) {
        this.rentAds = rentAds;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<UserPicture> getPictures() {
        return pictures;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setPictures(List<UserPicture> pictures) {
        this.pictures = pictures;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
