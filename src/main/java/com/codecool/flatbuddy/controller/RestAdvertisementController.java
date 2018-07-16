package com.codecool.flatbuddy.controller;

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

    @RequestMapping(value = "/user/advertisement/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<RentAd> findAdById(
            @PathVariable("id") int id) {
        return adService.getAdById(id);
    }

    @PutMapping("/user/deletead/{id}")
    public void deleteAd(@PathVariable("id") int id) throws SQLException {
        adService.deleteAdById(id);
    }

    @RequestMapping("/user/advertisements")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<RentAd> getAllAds() {return adService.getAllAds();}

    @PostMapping(value = "/user/advertisement",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RentAd addNewAdvertisement(@RequestBody RentAd rentAd) {
        adService.addNewAd(rentAd);
        return rentAd;
     }

}