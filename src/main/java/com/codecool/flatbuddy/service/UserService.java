package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class UserService {
    @Autowired
    private UserRepository repository;

    public Iterable<User> getAllUsers(){
        return repository.findAll();
    }
    public Optional<User> getUserById(Integer id){
        return repository.findById(id);
    }
    public User getUserByEmail(String email){
        return repository.findByEmail(email);
    }
}
