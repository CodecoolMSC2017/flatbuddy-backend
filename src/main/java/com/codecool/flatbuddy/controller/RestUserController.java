package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class RestUserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUserById(@PathVariable("id")int id){
        return userService.getUserById(id);
    }

}
