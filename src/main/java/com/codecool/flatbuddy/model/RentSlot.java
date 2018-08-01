package com.codecool.flatbuddy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "rent_slots")
public class RentSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rentad_id")
    private Integer rentAdId;
    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "renter_id", referencedColumnName = "id")
    private User renter;

    public RentSlot() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRentAdId() {
        return rentAdId;
    }

    public void setRentAdId(Integer rentAdId) {
        this.rentAdId = rentAdId;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }
}
