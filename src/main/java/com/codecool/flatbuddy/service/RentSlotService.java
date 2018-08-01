package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.AlreadyInASlotException;
import com.codecool.flatbuddy.model.RentSlot;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.RentSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentSlotService {

    @Autowired
    private RentSlotRepository repository;

    public List<RentSlot> getRentSlotsByRentAdId(int rentAdId) {
        return repository.findAllByRentAdId(rentAdId);
    }

    public void createRentSlotsForRentAd(int rentAdId,int numberOfSlots) {
        for (int i = 0;i < numberOfSlots;i++) {
            RentSlot slot = new RentSlot();
            slot.setRentAdId(rentAdId);
            repository.save(slot);
        }
    }

    public void addUserToSlot(int slotId,User user) throws AlreadyInASlotException {
        if (repository.findByRenter(user) == null) {
            RentSlot slot = repository.findById(slotId);
            slot.setRenter(user);
            repository.save(slot);
        }
        else {
            throw new AlreadyInASlotException("You already joined a slot.");
        }
    }

    public void removeUserFromSlot(int slotId) {
        repository.findById(slotId).setRenter(null);
    }

}
