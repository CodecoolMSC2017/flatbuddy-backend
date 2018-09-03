package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.AdComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdCommentRepository extends CrudRepository<AdComment,Integer> {
    //@Query(value = "select * from advertisement_comments where ad_id = :adid",nativeQuery = true)
    List<AdComment>findAllByAdId(Integer adId);
}
