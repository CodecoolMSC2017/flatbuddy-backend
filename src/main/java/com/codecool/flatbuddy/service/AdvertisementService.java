package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository adRepository;

    public Iterable<RentAd> getAllAds() {return adRepository.findAll();}
    public Optional<RentAd> getAdById(Integer id) {return adRepository.findById(id);}
    public void addNewAd(RentAd rentAd) {adRepository.save(rentAd);}
    public void deleteAdById(int id) {
        Optional<RentAd> advertisement = adRepository.findById(id);
        advertisement.get().setEnabled(false);
        adRepository.save(advertisement.get());
    }
}
