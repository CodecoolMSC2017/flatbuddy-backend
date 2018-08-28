package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<RentAd, Integer>, JpaSpecificationExecutor<RentAd> {
    RentAd findByUserAndId(User user, Integer adId);
    List<RentAd> findAllByUser(User user);
    @Query(value = "select * from rent_ads where is_deleted = false",nativeQuery = true)
    List<RentAd> findAll();
}
