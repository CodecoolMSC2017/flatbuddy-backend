package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.service.AdvertisementService;
import com.codecool.flatbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class RestAdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/users")
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id")int id){
        return userService.getUserById(id);
    }
    @GetMapping("/advertisements")
    public Iterable<RentAd> getAllAds(){
        return advertisementService.getAllAds();
    }
    @GetMapping("/advertisement/{id}")
    public Optional<RentAd> getAdvertisementById(@PathVariable("id")int id) throws UnauthorizedException {
        return advertisementService.getAdById(id);
    }
    @PostMapping("/user/edit/{id}")
    public void editUser(@RequestBody Map<String, Object> map, @PathVariable("id") int id){
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
