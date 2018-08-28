package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.exception.InvalidAdvertisementException;
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
    public Optional<RentAd> getAdvertisementById(@PathVariable("id")int id) throws UnauthorizedException, InvalidAdvertisementException {
        return advertisementService.getAdById(id);
    }
    @PostMapping("/user/edit/{id}")
    public void editUser(@RequestBody Map<String, Object> map, @PathVariable("id") int id){
        String firstName = (String) map.get("firstName");
        String lastName = (String) map.get("lastName");
        String description = (String) map.get("description");
        Boolean isFlatmate = (Boolean) map.get("isFlatmate");

        userService.updateUser(
                id, firstName, lastName,
                isFlatmate,description);
    }

}
