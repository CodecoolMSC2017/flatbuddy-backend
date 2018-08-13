package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.exception.RentSlotException;
import com.codecool.flatbuddy.model.RentSlot;
import com.codecool.flatbuddy.service.RentSlotService;
import com.codecool.flatbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestRentSlotController {

    @Autowired
    private RentSlotService rentSlotService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/user/advertisementslots/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RentSlot> getAllRentSlotsForRentAd(@PathVariable("id") int id) {
        return rentSlotService.getRentSlotsByRentAdId(id);
    }

    @PutMapping(path = "/user/advertisementslots/join/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void joinRentSlot(@PathVariable("id")int id) throws RentSlotException {
        rentSlotService.addUserToSlot(id,userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PutMapping(path = "/user/advertisementslots/leave/{id}/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void exitRentSlot(@PathVariable("id")int id,@PathVariable("userId")int userId) throws RentSlotException {
        rentSlotService.removeUserFromSlot(id,userService.getUserById(userId));
    }
}
