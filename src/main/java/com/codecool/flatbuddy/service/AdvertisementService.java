package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidAdvertisementException;
import com.codecool.flatbuddy.exception.RentSlotException;
import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.*;
import com.codecool.flatbuddy.repository.AdvertisementRepository;
import com.codecool.flatbuddy.util.DisabilityChecker;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedUser.getAuthorities().contains("ROLE_ADMIN")) {
            return adRepository.findAll();
        }
        return (List<RentAd>) DisabilityChecker.checkObjectsIsEnabled(adRepository.findAll());
    }

    public Optional<RentAd> getAdById(Integer id) throws UnauthorizedException, InvalidAdvertisementException {
        RentAd ad = adRepository.findById(id).get();
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!ad.isDeleted()) {
            if (ad.isEnabled() || loggedUser.getId().equals(ad.getUser().getId()) || loggedUser.getAuthorities().contains("ROLE_ADMIN")) {
                return adRepository.findById(id);
            }
            throw new UnauthorizedException("You can't view inactive advertisements.");
        }
        else {
            throw new InvalidAdvertisementException("Advertisement not found.");
        }
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

        if(rentAd == null){
            throw new InvalidAdvertisementException("Please fill all fields correctly");
        }

        if(rentAd.getCity().isEmpty() || rentAd.getCost() < 1 ||
                rentAd.getCountry().isEmpty() || rentAd.getDescription().isEmpty() ||
                rentAd.getSize() < 1 || rentAd.getState().isEmpty() ||
                rentAd.getStreet().isEmpty() || rentAd.getType().isEmpty() || rentAd.getZipCode().isEmpty()|| rentAd.getRoomsAvailable() < 1) {
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

    public void setAdVisibility(int id) throws RentSlotException, UnauthorizedException, InvalidAdvertisementException {
        RentAd advertisement = adRepository.findById(id).get();
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (advertisement.getUser().getId() == loggedUser.getId() || loggedUser.getAuthorities().contains("ROLE_ADMIN")) {
            if (advertisement.isEnabled()) {
                advertisement.setEnabled(false);
                List<RentSlot> slotsOfAd = rentSlotService.getRentSlotsByRentAdId(advertisement.getId());
                for (RentSlot slot : slotsOfAd) {
                    if (slot.getRenter() != null) {
                        rentSlotService.kickUserFromSlot(slot.getId(), slot.getRenter());
                    }
                }
            } else {
                advertisement.setEnabled(true);
            }
            adRepository.save(advertisement);
        }
        else {
            throw new UnauthorizedException("You can't modify other peoples advertisements.");
        }
    }

    public void deleteAdById(int id) throws UnauthorizedException, RentSlotException, InvalidAdvertisementException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedUser.getAuthorities().contains("ROLE_ADMIN")) {
            RentAd advertisement = adRepository.findById(id).get();
            List<RentSlot> slotsOfAd = rentSlotService.getRentSlotsByRentAdId(advertisement.getId());
            for (RentSlot slot : slotsOfAd) {
                if (slot.getRenter() != null) {
                    rentSlotService.kickUserFromSlot(slot.getId(), slot.getRenter());
                }
            }
            advertisement.setDeleted(true);
            adRepository.save(advertisement);

        }
        else {
            throw new UnauthorizedException("You don't have permission to delete advertisements.");
        }
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
    public void updateAdvertisement(UpdateRentAd rentAd) throws InvalidAdvertisementException, RentSlotException, UnauthorizedException {

        if(rentAd == null){
            throw new InvalidAdvertisementException("Please fill all fields correctly");
        }

        if(rentAd.getCity().isEmpty() || rentAd.getCost() < 1 ||
                rentAd.getCountry().isEmpty() || rentAd.getDescription().isEmpty() ||
                rentAd.getSize() < 1 || rentAd.getState().isEmpty() || rentAd.getRoomsAvailable() < 1 ||
                rentAd.getStreet().isEmpty() || rentAd.getType().isEmpty() || rentAd.getZipCode().isEmpty()){
            throw new InvalidAdvertisementException("You have to fill all the fields");
        }

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
        if (advertisement.getRoomsAvailable() < rentAd.getRoomsAvailable()) {
            int difference =rentAd.getRoomsAvailable() - advertisement.getRoomsAvailable();
            for (int i = 0;i < difference;i++) {
                rentSlotService.createRentSlot(advertisement.getId());
            }
        }
        else if (advertisement.getRoomsAvailable() > rentAd.getRoomsAvailable()) {
            List<RentSlot> slotsOfAd =advertisement.getSlots();
            for (RentSlot slot:slotsOfAd) {
                if(slot.getRenter() != null) {
                    rentSlotService.kickUserFromSlot(slot.getId(), slot.getRenter());
                }
            }
            rentSlotService.deleteAllByRentAdId(advertisement.getId());
            rentSlotService.createRentSlotsForRentAd(advertisement.getId(),rentAd.getRoomsAvailable());

        }
        advertisement.setRoomsAvailable(rentAd.getRoomsAvailable());
        advertisement.setSize(rentAd.getSize());
        advertisement.setState(rentAd.getState());
        advertisement.setStreet(rentAd.getStreet());
        advertisement.setType(rentAd.getType());
        advertisement.setZipCode(rentAd.getZipCode());

        adRepository.save(advertisement);


    }
    public List<RentAd> getAllAds(Specification<RentAd> spec){
        return adRepository.findAll(spec);
    }

    public void setAdToPremium(Integer rentAdId){
        RentAd advertisement = adRepository.findById(rentAdId).get();
        advertisement.setPremium(true);
        adRepository.save(advertisement);
    }
}
