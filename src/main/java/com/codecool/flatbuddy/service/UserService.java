package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.UserRepository;
import com.codecool.flatbuddy.util.DisabilityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class UserService {
    @Autowired
    private UserRepository repository;

    public Iterable<User> getAllUsers() {
        List<User> users = (List<User>) repository.findAll();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            List<RentAd> adsOfUser = user.getRentAds();
            user.setRentAds((List<RentAd>) DisabilityChecker.checkObjectsIsEnabled(adsOfUser));
        }
        return users;
    }

    public Optional<User> getUserById(Integer id){
        User user = repository.findById(id).get();
        List<RentAd> ads = user.getRentAds();
        user.setRentAds((List<RentAd>) DisabilityChecker.checkObjectsIsEnabled(ads));
        return Optional.ofNullable(user);

    }

    public User getUserByEmail(String email){
        return repository.findByEmail(email);
    }

    public List<User> getFlatmates(boolean flatmate){
        return repository.findAllByisFlatmate(flatmate);
    }


}
