package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface MatchRepository extends CrudRepository<Match,Integer> {
}
