package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.model.Match;
import com.codecool.flatbuddy.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    public void addToMatches(Integer id){
        Match match = new Match();
        match.setUserA(1); // logged in user
        match.setUserB(id);
        match.setMatched(true);
        matchRepository.save(match);
        Match match2 = new Match();
        match2.setUserA(match.getUserB());
        match2.setUserB(match.getUserA());
        match2.setMatched(false);
        matchRepository.save(match2);
    }

    public List<Match> getByUserA(Integer userId){
        return matchRepository.findAllByuserA(userId);
    }
    public List<Match> getByUserB(Integer userId){
        return matchRepository.findAllByuserB(userId);
    }
    public Match getByUserAAndUserB(Integer userA, Integer userB){
        return matchRepository.findByUserAAndUserB(userA,userB);
    }
    public Match getByUserBAndUserA(Integer userB, Integer userA){
        return matchRepository.findByUserBAndUserA(userB,userA);
    }
    private void deleteMatchByUserA(Integer userA){
        matchRepository.deleteById(userA);
    }
    private void deleteMatchByUserB(Integer userB){
        matchRepository.deleteById(userB);
    }
    public void deleteMatchByUserAandUserB(Integer userA,Integer userB){
        deleteMatchByUserA(userA);
        deleteMatchByUserB(userB);
    }
}
