package com.codecool.flatbuddy.model;

import javax.persistence.*;

@Entity
@Table(name = "rent_slots")
public class RentSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rentad_id")
    private Integer rentAdId;

    @Column(name = "renter_id")
    private Integer renterId;

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

    public Integer getRenterId() {
        return renterId;
    }

    public void setRenterId(Integer renterId) {
        this.renterId = renterId;
    }
}
