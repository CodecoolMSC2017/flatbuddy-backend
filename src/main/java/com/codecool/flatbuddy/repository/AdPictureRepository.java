package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.AdPicture;
import com.codecool.flatbuddy.model.UserPicture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdPictureRepository extends CrudRepository<AdPicture,Integer> {
}
