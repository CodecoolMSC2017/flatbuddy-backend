package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MatchRepository extends CrudRepository<Match,Integer> {
    List<Match> findAllByuserA(Integer userA);
    List<Match> findAllByuserB(Integer userB);
    Match findByUserAAndUserBId(Integer userA, Integer userB);
    Match findByUserBIdAndUserA(Integer userB, Integer userA);
    @Query(value ="select * from matches where user_a = ?1 and status != 2;",nativeQuery = true)
    List<Match> findAllPending(Integer userId);
}
