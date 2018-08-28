package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidAdvertisementException;
import com.codecool.flatbuddy.exception.RentSlotException;
import com.codecool.flatbuddy.exception.UnauthorizedException;
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

    @Autowired
    private AdvertisementService advertisementService;


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

    public void deleteAllByRentAdId(int rentAdId) {
        repository.deleteAllByRentAdId(rentAdId);
    }

    public void createRentSlot(int rentAdId) {
        RentSlot slot = new RentSlot();
        slot.setRentAdId(rentAdId);
        repository.save(slot);
    }

    public void deleteSlot(RentSlot slot) {
        repository.delete(slot);
    }

    public void joinSlot(int slotId,User user) throws RentSlotException, UnauthorizedException, InvalidAdvertisementException {
        if (repository.findByRenter(user) == null) {
            RentSlot slot = repository.findById(slotId);
            slot.setRenter(user);
            repository.save(slot);
            notificationService.createNotification(advertisementService.getAdById(slot.getRentAdId()).get().getUser().getId(),user.getFirstName() + " joined your advertisement.",NotificationTypeEnum.ADVERTISEMENT.getValue(),slot.getRentAdId());
            List<RentSlot> rentSlotsOfAdvertisement=repository.findAllByRentAdId(repository.findById(slotId).getRentAdId());
            int counter=0;
            for (RentSlot rentSlot:rentSlotsOfAdvertisement) {
                if (rentSlot.getRenter() != null) {
                    counter++;
                }
            }
            if (counter == rentSlotsOfAdvertisement.size()) {
                notificationService.createNotification(advertisementService.getAdById(slot.getRentAdId()).get().getUser().getId(),"Your advertisement is full,every slot has a user.",NotificationTypeEnum.ADVERTISEMENT.getValue(),slot.getRentAdId());
            }
        }

        else {
            throw new RentSlotException("You already joined a slot.");
        }
    }

    public void leaveSlot(int slotId,User user) throws RentSlotException, UnauthorizedException, InvalidAdvertisementException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedInUser.getId().equals(user.getId())) {
            RentSlot slot = repository.findById(slotId);
            slot.setRenter(null);
            repository.save(slot);
            notificationService.createNotification(advertisementService.getAdById(slot.getRentAdId()).get().getUser().getId(),user.getFirstName() + " left your advertisement.",NotificationTypeEnum.ADVERTISEMENT.getValue(),slot.getRentAdId());
        }
        else {
            throw new RentSlotException("You cant remove other users from a rentslot.");
        }
    }

    public void kickUserFromSlot(int slotId,User user) throws RentSlotException, UnauthorizedException, InvalidAdvertisementException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        User adOwner =advertisementService.getAdById(repository.findById(slotId).getRentAdId()).get().getUser();
        if (loggedInUser.getId().equals(adOwner.getId()) || loggedInUser.getAuthorities().contains("ROLE_ADMIN")) {
            RentSlot slot = repository.findById(slotId);
            slot.setRenter(null);
            repository.save(slot);
            if (loggedInUser.getId() == adOwner.getId()) {
                notificationService.createNotification(user.getId(), adOwner.getFirstName() + " kicked you from his/her advertisement.", NotificationTypeEnum.ADVERTISEMENT.getValue(), slot.getRentAdId());
            }
            else {
                notificationService.createNotification(user.getId(), "You have been removed from the slot by the admin.", NotificationTypeEnum.ADVERTISEMENT.getValue(), slot.getRentAdId());
            }
        }
        else {
            throw new RentSlotException("You can't remove users from other peoples advertisement.");
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
