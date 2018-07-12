package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.model.Match;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.MatchRepository;
import com.codecool.flatbuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public final class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private MatchRepository matchRepository;

    public Iterable<User> getAllUsers(){
        return repository.findAll();
    }
    public Optional<User> getUserById(Integer id){
        return repository.findById(id);
    }
    public User getUserByEmail(String email){
        return repository.findByEmail(email);
    }
    public List<User> getFlatmates(boolean flatmate){
        return repository.findAllByisFlatmate(flatmate);
    }

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

}
