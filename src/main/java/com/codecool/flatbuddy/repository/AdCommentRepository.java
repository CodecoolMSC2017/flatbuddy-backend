package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.AdComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdCommentRepository {
    List<AdComment> findAllByAdId(Integer adid);
}
