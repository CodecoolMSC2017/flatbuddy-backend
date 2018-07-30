package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.model.AdPicture;
import com.codecool.flatbuddy.model.NewRentAd;
import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Component
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository adRepository;

    @Autowired
    private UserService userService;

    public Iterable<RentAd> getAllAds() {
        return adRepository.findAll();
    }

    public Optional<RentAd> getAdById(Integer id) {
        return adRepository.findById(id);
    }

    public void addNewAd(NewRentAd rentAd) {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        RentAd advertisement = new RentAd();
        advertisement.setUser(loggedUser);
        advertisement.setPremium(false);
        advertisement.setEnabled(true);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.format(currentDate);
        advertisement.setPublishedDate(currentDate);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Date expirationDate = cal.getTime();
        advertisement.setExpirationDate(expirationDate);

        advertisement.setCity(rentAd.getCity());
        advertisement.setCost(rentAd.getCost());
        advertisement.setCountry(rentAd.getCountry());
        advertisement.setDescription(rentAd.getDescription());
        advertisement.setDistrict(rentAd.getDistrict());
        if(rentAd.isFurnitured()== null){
            advertisement.setFurnitured(false);
        }
        else{
            advertisement.setFurnitured(rentAd.isFurnitured());
        }

        advertisement.setRoomsAvailable(rentAd.getRoomsAvailable());
        advertisement.setSize(rentAd.getSize());
        advertisement.setState(rentAd.getState());
        advertisement.setStreet(rentAd.getStreet());
        advertisement.setType(rentAd.getType());
        advertisement.setZipCode(rentAd.getZipCode());

        advertisement.setAdPictures(new ArrayList<>());
        advertisement.setSlots(new ArrayList<>());
        advertisement.setAdSubscriptions(new ArrayList<>());



        adRepository.save(advertisement);
    }

    public void deleteAdById(int id) {
        Optional<RentAd> advertisement = adRepository.findById(id);
        advertisement.get().setEnabled(false);
        adRepository.save(advertisement.get());
    }
}
