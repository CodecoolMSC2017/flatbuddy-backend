package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.RentSlotException;
import com.codecool.flatbuddy.model.RentSlot;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.model.enums.NotificationTypeEnum;
import com.codecool.flatbuddy.repository.RentSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.ManyToOne;
import java.util.List;

@Component
public class RentSlotService {

    @Autowired
    private RentSlotRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MatchService matchService;


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

    public void addUserToSlot(int slotId,User user) throws RentSlotException {
        if (repository.findByRenter(user) == null) {
            RentSlot slot = repository.findById(slotId);
            slot.setRenter(user);
            repository.save(slot);
        }

        else {
            throw new RentSlotException("You already joined a slot.");
        }
    }

    public void removeUserFromSlot(int slotId,User user) throws RentSlotException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedInUser.getId().equals(user.getId())) {
            RentSlot slot = repository.findById(slotId);
            slot.setRenter(null);
            repository.save(slot);
        }
        else {
            throw new RentSlotException("You cant remove other users from a rentslot.");
        }
    }

    public void inviteUserToSlot(int slotId,int userId) throws RentSlotException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (repository.findById(slotId).getRenter() != null) {
            throw new RentSlotException("This slot is already taken");
        }
        if (matchService.getByUserAAndUserB(loggedInUser.getId(),userId) == null) {
            throw new RentSlotException("You aren't matched with this user.");
        }
        if (matchService.getByUserAAndUserB(loggedInUser.getId(),userId).getStatus() != 2) {
            throw new RentSlotException("You can't invite people who aren't matched with you.");
        }
        int rentAdId =repository.findById(slotId).getRentAdId();
        notificationService.createNotification(userId,loggedInUser.getFirstName() + " invited you to an advertisement.", NotificationTypeEnum.SLOT.getValue(),rentAdId);
    }

}
