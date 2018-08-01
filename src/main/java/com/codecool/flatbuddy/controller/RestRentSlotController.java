package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.model.RentSlot;
import com.codecool.flatbuddy.service.RentSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestRentSlotController {

    @Autowired
    private RentSlotService rentSlotService;

    @GetMapping(path = "/user/advertisementslots/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RentSlot> getAllRentSlotsForRentAd(@PathVariable("id") int id) {
        return rentSlotService.getRentSlotsByRentAdId(id);
    }
}
