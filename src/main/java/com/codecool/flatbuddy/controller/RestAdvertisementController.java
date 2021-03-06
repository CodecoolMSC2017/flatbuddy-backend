package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.exception.InvalidAdvertisementException;
import com.codecool.flatbuddy.exception.InvalidNotificationTypeException;
import com.codecool.flatbuddy.exception.RentSlotException;
import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.AdComment;
import com.codecool.flatbuddy.model.NewRentAd;
import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.UpdateRentAd;
import com.codecool.flatbuddy.service.AdvertisementService;
import com.codecool.flatbuddy.util.AdvertisementSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class RestAdvertisementController {
    @Autowired
    private AdvertisementService adService;

    @GetMapping(value = "/advertisement/{id}")
    @ResponseBody
    public Optional<RentAd> findAdById(
            @PathVariable("id") int id) throws UnauthorizedException, InvalidAdvertisementException {
        return adService.getAdById(id);
    }

    @DeleteMapping("/admin/deletead/{id}")
    public void deleteAd(@PathVariable("id") int id) throws SQLException, UnauthorizedException, RentSlotException, InvalidAdvertisementException {
        adService.deleteAdById(id);
    }
    @PutMapping("/user/advertisement/setactivity/{id}")
    public void setAdVisibility(@PathVariable("id") int id) throws SQLException, InvalidAdvertisementException, RentSlotException, UnauthorizedException {
        adService.setAdVisibility(id);
    }

    @GetMapping(path = "/advertisements", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<RentAd> getAllAds() {return adService.getAllAds();}

    @GetMapping(path = "/advertisements/search/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    //public Iterable<RentAd> getAllAds() {return adService.getAllAds();}
    public List<RentAd> search(@PathVariable("search") String search) {
        AdvertisementSpecificationBuilder builder = new AdvertisementSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<RentAd> spec = builder.build();
        return adService.getAllAds(spec);
    }

    @PostMapping(value = "/user/advertisement")
    public NewRentAd addNewAdvertisement(@RequestBody NewRentAd rentAd) throws InvalidAdvertisementException, UnauthorizedException, InvalidNotificationTypeException {
        adService.addNewAd(rentAd);
        return rentAd;
     }

    @GetMapping(path = "/user/myadvertisements", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RentAd> getAllAdsForLoggedInUser() throws InvalidAdvertisementException {
        return adService.getAdsByUser();
    }

    @PostMapping(path = "/user/advertisement/update")
    public void updateAdvertisement(@RequestBody UpdateRentAd rentAd) throws InvalidAdvertisementException, RentSlotException, UnauthorizedException {
        adService.updateAdvertisement(rentAd);
    }

    @GetMapping(value = "/user/myadvertisement/{id}")
    @ResponseBody
    public Optional<RentAd> findMyAdById(
            @PathVariable("id") int id) throws UnauthorizedException, InvalidAdvertisementException {
        return adService.getMyAdById(id);
    }

    @GetMapping("/advertisement/comments/{id}")
    public List<AdComment> getComments(@PathVariable("id") int id) throws SQLException{
        return adService.getCommentsForAd(id);
    }

}