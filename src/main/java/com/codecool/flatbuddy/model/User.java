package com.codecool.flatbuddy.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private String role;
    @Column(name = "is_flatmate")
    private boolean isFlatmate;
    private int age;
    private String gender;
    private String description;
    private String destination;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isFlatmate() {
        return isFlatmate;
    }

    public void setFlatmate(boolean flatmate) {
        isFlatmate = flatmate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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
}
