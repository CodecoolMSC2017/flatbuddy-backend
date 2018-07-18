package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User add(@RequestBody Map<String, String> map) {
        String newEmail = map.get("email");
        String newPassword = map.get("password");
        String newConfirmationPassword = map.get("confirmationPassword");
        return userService.addNewUser(newEmail, newPassword, newConfirmationPassword);
    }
}
