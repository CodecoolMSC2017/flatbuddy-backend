package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.model.NewRentAd;
import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;


@RestController
public class RestAdvertisementController {
    @Autowired
    private AdvertisementService adService;

    @GetMapping(value = "/user/advertisement/{id}")
    @ResponseBody
    public Optional<RentAd> findAdById(
            @PathVariable("id") int id) {
        return adService.getAdById(id);
    }

    @PutMapping("/user/deletead/{id}")
    public void deleteAd(@PathVariable("id") int id) throws SQLException {
        adService.deleteAdById(id);
    }

    @GetMapping(path = "/user/advertisements", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<RentAd> getAllAds() {return adService.getAllAds();}

    @PostMapping(value = "/user/advertisement")
    public NewRentAd addNewAdvertisement(@RequestBody NewRentAd rentAd) {
        adService.addNewAd(rentAd);
        return rentAd;
     }

}