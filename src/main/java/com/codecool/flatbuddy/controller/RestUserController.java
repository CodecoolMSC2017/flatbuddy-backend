package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.model.Match;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.service.MatchService;
import com.codecool.flatbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class RestUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MatchService matchService;

    @GetMapping(path = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUserById(@PathVariable("id")int id){
        return userService.getUserById(id);
    }
    @GetMapping
    public User getUserByEmail(@RequestParam("email") String email){
        return userService.getUserByEmail(email);
    }
    @GetMapping("/flatmates")
    public List<User> getFlatmates(){
        return userService.getFlatmates(true);
    }
    @PostMapping(path = "/match/send/{id}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMatchRequest(@PathVariable("id")int id){
        matchService.addToMatches(id);
    }

    @GetMapping(path = "/matches/{id}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Match> getMatchesByUserAId(@PathVariable("id")int id){
        return matchService.getByUserA(id);
    }
    @DeleteMapping(path = "/match/delete/{id}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteMatches(@PathVariable("id")int id){
        matchService.deleteMatchByUserAandUserB(id,1);//ide kell a bejelentkezett user!!!
    }


}
