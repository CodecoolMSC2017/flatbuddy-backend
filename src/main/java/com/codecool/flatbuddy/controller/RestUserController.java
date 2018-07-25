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

    @GetMapping(path = "/{id}",
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

    @PostMapping("/profile-update")
    public void profileUpdate(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        String age = map.get("age");
        String gender = map.get("gender");
        String description = map.get("description");
        String destination = map.get("destination");
        String isFlatmate = map.get("isFlatmate");
        String oldPw = map.get("oldPw");
        String newPw = map.get("newPw");
        String confirmationPw = map.get("confirmationPw");

        userService.updateUser(
                Integer.parseInt(id), firstName, lastName, Integer.parseInt(age),
                oldPw, newPw, confirmationPw,
                Boolean.parseBoolean(isFlatmate), gender, description, destination);
    }

}
