package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.RentSlot;
import com.codecool.flatbuddy.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RentSlotRepository extends CrudRepository<RentSlot, Integer> {
    List<RentSlot> findAllByRentAdId(int rentAdId);
    RentSlot findById(int slotId);
    RentSlot findByRenter(User user);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM rent_slots WHERE rentad_id =?1",nativeQuery = true)
    void deleteAllByRentAdId(int rentAdId);
}
