package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class RestAuthController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public User get(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

    @PostMapping("")
    public User authenticateUserByToken(@RequestBody Map<String, String> map) throws GeneralSecurityException, IOException {
        return userService.getUserByToken(map.get("idToken"));
    }



    @DeleteMapping("")
    public void delete(HttpSession session) {
        session.invalidate();
    }
}
