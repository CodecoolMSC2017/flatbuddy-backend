package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.AdComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdCommentRepository extends CrudRepository<AdComment,Integer> {
    List<AdComment> findAllByAdId(Integer adid);
}
