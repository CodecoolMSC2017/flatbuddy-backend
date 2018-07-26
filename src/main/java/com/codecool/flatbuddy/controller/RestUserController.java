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
    public void profileUpdate(@RequestBody Map<String, Object> map) {
        Integer id = (Integer) map.get("id");
        String firstName = (String) map.get("firstName");
        String lastName = (String) map.get("lastName");
        Integer age = (Integer) map.get("age");
        String gender = (String) map.get("gender");
        String description = (String) map.get("description");
        String destination = (String) map.get("destination");
        Boolean isFlatmate = (Boolean) map.get("isFlatmate");
        String oldPw = (String) map.get("oldPw");
        String newPw = (String) map.get("newPw");
        String confirmationPw = (String) map.get("confirmationPw");

        userService.updateUser(
                id, firstName, lastName, age,
                oldPw, newPw, confirmationPw,
                isFlatmate, gender, description, destination);
    }

}
