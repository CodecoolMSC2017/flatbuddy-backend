package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MatchRepository extends CrudRepository<Match,Integer> {
    List<Match> findAllByuserA(Integer userA);
    List<Match> findAllByuserB(Integer userB);
    Match findByUserAAndUserB(Integer userA, Integer userB);
    Match findByUserBAndUserA(Integer userB, Integer userA);
    List<Match> findAllByUserAAndStatus(Integer userA, Integer status);
    //List<Match> findByuserBAndisFlatmate(Integer userB, Boolean isFlatmate);
}
