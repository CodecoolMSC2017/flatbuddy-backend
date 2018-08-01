package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidAdvertisementException;
import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.*;
import com.codecool.flatbuddy.repository.AdvertisementRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Component
public class AdvertisementService {
    private final int EXPIRATION_TIME = 1; // 1 month

    @Autowired
    private AdvertisementRepository adRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RentSlotService rentSlotService;

    public Iterable<RentAd> getAllAds() {
        return adRepository.findAll();
    }

    public Optional<RentAd> getAdById(Integer id) {
        return adRepository.findById(id);
    }

    public Optional<RentAd> getMyAdById(Integer id) throws UnauthorizedException {
        if(isAdvertisementMine(adRepository.findById(id).get().getId())) {
            return adRepository.findById(id);
        }
        else {
            throw new UnauthorizedException("Access denied");
        }
    }

    public void addNewAd(NewRentAd rentAd) throws InvalidAdvertisementException, UnauthorizedException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(loggedUser == null){
            throw new UnauthorizedException("Access denied");
        }

        if(rentAd.getCity().isEmpty() || rentAd.getCost() < 1 ||
                rentAd.getCountry().isEmpty() || rentAd.getDescription().isEmpty() ||
                rentAd.getSize() < 1 || rentAd.getState().isEmpty() ||
                rentAd.getStreet().isEmpty() || rentAd.getType().isEmpty() || rentAd.getZipCode().isEmpty()){
            throw new InvalidAdvertisementException("You have to fill all the fields");
        }

        RentAd advertisement = new RentAd();
        advertisement.setUser(loggedUser);
        advertisement.setPremium(false);
        advertisement.setEnabled(true);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.format(currentDate);
        advertisement.setPublishedDate(currentDate);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, EXPIRATION_TIME);
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
        rentSlotService.createRentSlotsForRentAd(advertisement.getId(),rentAd.getRoomsAvailable());

    }

    public void deleteAdById(int id) {
        Optional<RentAd> advertisement = adRepository.findById(id);
        advertisement.get().setEnabled(false);
        adRepository.save(advertisement.get());
    }

    public boolean isAdvertisementMine(Integer adId){
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(adRepository.findByUserAndId(loggedUser,adId) != null){
            return true;
        }
        return false;
    }

    public List<RentAd> getAdsByUser() throws InvalidAdvertisementException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (adRepository.findAllByUser(loggedUser) != null){
            return adRepository.findAllByUser(loggedUser);
        }
        else{
            throw new InvalidAdvertisementException("You don't have any advertisements.");
        }
    }
    public void updateAdvertisement(UpdateRentAd rentAd){

        RentAd advertisement = getAdById(rentAd.getId()).get();
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

        adRepository.save(advertisement);
    }
}
