package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.model.Match;
import com.codecool.flatbuddy.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestMatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping(path = "/user/match/send/{id}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMatchRequest(@PathVariable("id")int id){
        matchService.addToMatches(id);
    }



    @GetMapping(path = "/user/matches/{id}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Match> getMatchesByUserAId(@PathVariable("id")int id){
        return matchService.getByUserA(id);
    }
    @DeleteMapping(path = "/user/match/delete/{id}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteMatch(@PathVariable("id")int id){
        matchService.deleteMatch(id,1);//ide kell a bejelentkezett user!!!
    }

    @PutMapping(path = "/user/match/accept/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void acceptMatch(@PathVariable("id")int id){
        matchService.acceptMatch(id);
    }
}
