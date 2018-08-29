package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidMatchException;
import com.codecool.flatbuddy.model.Match;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.model.enums.MatchStatusEnum;
import com.codecool.flatbuddy.model.enums.NotificationTypeEnum;
import com.codecool.flatbuddy.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    public void addToMatches(Integer id) throws InvalidMatchException {
        Integer loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        if(loggedUser.equals(id)){
            throw new InvalidMatchException("You cant send request to yourself");
        }
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(userService.isUserDetailsNotDone(loggedInUser)){
            throw new InvalidMatchException("You have to fill yor profile to access this feature");
        }

        Match a = matchRepository.findByUserAAndUserBId(loggedUser,id);
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
        notificationService.createNotification(id,userService.getUserById(loggedUser).getFirstName() + " sent a matchrequest.", NotificationTypeEnum.MATCH.getValue(),loggedUser);
        Match match = new Match();
        match.setUserA(loggedUser); // logged in user
        match.setUserB(userService.getUserById(id));
        match.setStatus(MatchStatusEnum.SENTPENDING.getValue());
        matchRepository.save(match);
        Match match2 = new Match();
        match2.setUserA(match.getUserB().getId());
        match2.setUserB(userService.getUserById(match.getUserA()));
        match2.setStatus(MatchStatusEnum.RECEIVEDPENDING.getValue());
        matchRepository.save(match2);
    }

    public void acceptMatch(Integer matchId) {
        Match userASideMatch =  matchRepository.findById(matchId).get();
        int userA = userASideMatch.getUserA();
        int userB = userASideMatch.getUserB().getId();
        Match  userBSideMatch= matchRepository.findByUserAAndUserBId(userB,userA);
        userASideMatch.setStatus(MatchStatusEnum.ACCEPTED.getValue());
        userBSideMatch.setStatus(MatchStatusEnum.ACCEPTED.getValue());
        matchRepository.save(userASideMatch);
        matchRepository.save(userBSideMatch);
        notificationService.createNotification(userB,userService.getUserById(userA).getFirstName() + " accepted your matchrequest.",NotificationTypeEnum.MATCH.getValue(),userA);

    }

    public List<Match> getByUserA(Integer userId){
        List<Match> matches = new ArrayList<>();
        for (Match match: matchRepository.findAllByuserA(userId)) {
            if (userService.getUserById(match.getUserB().getId()).isEnabled()){
                matches.add(match);
            }
        }
        return matches;
    }

    public List<Match> getByUserB(Integer userId){
        return matchRepository.findAllByuserB(userId);
    }

    public Match getByUserAAndUserB(Integer userA, Integer userB){
        return matchRepository.findByUserAAndUserBId(userA,userB);
    }

    public Match getByUserBAndUserA(Integer userB, Integer userA){
        return matchRepository.findByUserBIdAndUserA(userB,userA);
    }

    public void deleteMatch(Integer userA,Integer userB) {
        matchRepository.delete(matchRepository.findByUserAAndUserBId(userA,userB));
        matchRepository.delete(matchRepository.findByUserBIdAndUserA(userA,userB));
    }

    public List<Match> findAllPending() {
        return matchRepository.findAllPending(userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId()); // 1 a userId, aki be van lépve
    }
}
