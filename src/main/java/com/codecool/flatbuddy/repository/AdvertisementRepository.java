package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.RentAd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends CrudRepository<RentAd, Integer> {

}
