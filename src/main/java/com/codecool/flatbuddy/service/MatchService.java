package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidMatchException;
import com.codecool.flatbuddy.model.Match;
import com.codecool.flatbuddy.model.enums.MatchStatusEnum;
import com.codecool.flatbuddy.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserService userService;

    public void addToMatches(Integer id) throws InvalidMatchException {
        Integer loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        if(loggedUser.equals(id)){
            throw new InvalidMatchException("You cant send request to yourself");
        }
        Match a = matchRepository.findByUserAAndUserB(loggedUser,id);
        if(a != null) {
            if (matchRepository.existsById(a.getId())) {
                if (a.getStatus() == MatchStatusEnum.SENTPENDING.getValue()) {
                    throw new InvalidMatchException("You already sent a request to this user");
                }
                if (a.getStatus() == MatchStatusEnum.ACCEPTED.getValue()) {
                    throw new InvalidMatchException("You already matched with this user");
                }
            }
        }

        Match match = new Match();
        match.setUserA(loggedUser); // logged in user
        match.setUserB(id);
        match.setStatus(MatchStatusEnum.SENTPENDING.getValue());
        matchRepository.save(match);
        Match match2 = new Match();
        match2.setUserA(match.getUserB());
        match2.setUserB(match.getUserA());
        match2.setStatus(MatchStatusEnum.RECEIVEDPENDING.getValue());
        matchRepository.save(match2);
    }

    public void acceptMatch(Integer matchId) {
        Match userASideMatch =  matchRepository.findById(matchId).get();
        int userA = userASideMatch.getUserA();
        int userB = userASideMatch.getUserB();
        Match  userBSideMatch= matchRepository.findByUserAAndUserB(userB,userA);
        userASideMatch.setStatus(MatchStatusEnum.ACCEPTED.getValue());
        userBSideMatch.setStatus(MatchStatusEnum.ACCEPTED.getValue());
        matchRepository.save(userASideMatch);
        matchRepository.save(userBSideMatch);

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

    public void deleteMatch(Integer userA,Integer userB) {
        matchRepository.delete(matchRepository.findByUserAAndUserB(userA,userB));
        matchRepository.delete(matchRepository.findByUserBAndUserA(userA,userB));
    }

    public List<Match> getAllByUserAAndStatus(Integer status) {
        return matchRepository.findAllByUserAAndStatus(userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId(), status); // 1 a userId, aki be van lépve
    }
}
