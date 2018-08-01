package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.exception.InvalidAdvertisementException;
import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.NewRentAd;
import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.UpdateRentAd;
import com.codecool.flatbuddy.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
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
    public void deleteAd(@PathVariable("id") int id) throws SQLException, InvalidAdvertisementException {
        if(adService.isAdvertisementMine(id)) {
            adService.deleteAdById(id);
        }
        else {
            throw new InvalidAdvertisementException("Can't delete others advertisement");
        }
    }

    @GetMapping(path = "/user/advertisements", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<RentAd> getAllAds() {return adService.getAllAds();}

    @PostMapping(value = "/user/advertisement")
    public NewRentAd addNewAdvertisement(@RequestBody NewRentAd rentAd) throws InvalidAdvertisementException, UnauthorizedException {
        adService.addNewAd(rentAd);
        return rentAd;
     }

    @GetMapping(path = "/user/myadvertisements", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RentAd> getAllAdsForLoggedInUser() throws InvalidAdvertisementException {
        return adService.getAdsByUser();
    }

    @PostMapping(path = "/user/advertisement/update")
    public void updateAdvertisement(@RequestBody UpdateRentAd rentAd) throws InvalidAdvertisementException {
        if(adService.isAdvertisementMine(rentAd.getId())) {
            adService.updateAdvertisement(rentAd);
        }
        else{
            throw new InvalidAdvertisementException("Can't edit others advertisement");
        }
    }

}