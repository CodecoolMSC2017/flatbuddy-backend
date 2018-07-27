package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.exception.InvalidMatchException;
import com.codecool.flatbuddy.model.Match;
import com.codecool.flatbuddy.service.MatchService;
import com.codecool.flatbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestMatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private UserService userService;

   // private Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @PostMapping(path = "/user/match/send/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMatchRequest(@PathVariable("id")int id){
        try {
            matchService.addToMatches(id);
        } catch (InvalidMatchException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/user/matches/{id}")
    public List<Match> getMatchesByUserAId(@PathVariable("id")int id){
        return matchService.getByUserA(id);
    }

    @DeleteMapping(path = "/user/match/delete/{id}")
    public void deleteMatch(@PathVariable("id")int id){
        matchService.deleteMatch(id,userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId());//ide kell a bejelentkezett user!!!
    }

    @PutMapping(path = "/user/match/accept/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptMatch(@PathVariable("id")int id){
        matchService.acceptMatch(id);
    }

    @GetMapping(path = "/user/matches/status/{statusNumber}")
    public List<Match> getMatchesByUserAIdAndStatus(@PathVariable("statusNumber") int statusNumber) {
        return matchService.getAllByUserAAndStatus(statusNumber);
    }
}
