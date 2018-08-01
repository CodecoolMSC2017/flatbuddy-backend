package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.RentSlot;
import com.codecool.flatbuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentSlotRepository extends CrudRepository<RentSlot, Integer> {
    List<RentSlot> findAllByRentAdId(int rentAdId);
    RentSlot findById(int slotId);
    RentSlot findByRenter(User user);
}
