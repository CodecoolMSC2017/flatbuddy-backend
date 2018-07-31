package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends CrudRepository<RentAd, Integer> {
    RentAd findByUserAndId(User user, Integer adId);
    List<RentAd> findAllByUser(User user);
}
