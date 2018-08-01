package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.exception.AlreadyInASlotException;
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
    public void joinRentSlot(@PathVariable("id")int id) throws AlreadyInASlotException {
        rentSlotService.addUserToSlot(id,userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
