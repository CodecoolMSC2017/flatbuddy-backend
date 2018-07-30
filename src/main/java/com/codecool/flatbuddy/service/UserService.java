package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.UserNotFoundException;
import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.UserRepository;
import com.codecool.flatbuddy.util.DisabilityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserDetailsManager userManager;

    @Autowired
    private PasswordEncoder pwEncoder;

    public Iterable<User> getAllUsers() {
        List<User> users = (List<User>) repository.findAll();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            List<RentAd> adsOfUser = user.getRentAds();
            user.setRentAds((List<RentAd>) DisabilityChecker.checkObjectsIsEnabled(adsOfUser));
        }
        return users;
    }

    public Optional<User> getUserById(Integer id){
        User user = repository.findById(id).get();
        List<RentAd> ads = user.getRentAds();
        user.setRentAds((List<RentAd>) DisabilityChecker.checkObjectsIsEnabled(ads));
        return Optional.ofNullable(user);

    }

    public void updateUser(int id, String firstName, String lastName, Integer age,
                           String oldPw, String newPw, String confirmationPw, boolean isFlatmate, String gender,
                           String description, String destination) {
        User loggedInUser = getUserById(id).get();

        loggedInUser.setFirstName(firstName);
        loggedInUser.setLastName(lastName);
        loggedInUser.setGender(gender);
        loggedInUser.setAge(age);
        loggedInUser.setDescription(description);
        loggedInUser.setDestination(destination);
        loggedInUser.setFlatmate(isFlatmate);

        if (oldPw != null && newPw != null && confirmationPw != null) {
            changePw(oldPw, newPw, confirmationPw);
        }
        repository.save(loggedInUser);

    }

    public void changePw(String oldPw, String newPw, String confirmationPw) {
        if (!newPw.equals(confirmationPw)) {
            throw new IllegalArgumentException("New password must be same as confirmation password!");
        }

        String encodedNewPassword = pwEncoder.encode(newPw);
        userManager.changePassword(oldPw, encodedNewPassword);
    }


    public User getUserByEmail(String email){
        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException(email + " does not found!");
        }

        return user;
    }

    public List<User> getFlatmates(int userId){
        return repository.findPeople(userId);
    }

    public User addNewUser(String email, String password, String confirmationPassword) {
        if (isEmailExists(email)) {
            throw new IllegalArgumentException("This email address already exists, please choose another one!");
        }

        if (email.equals("") || password.equals("") || confirmationPassword.equals("")) {
            throw new IllegalArgumentException("Fill out each inputs!");
        }

        if (!password.equals(confirmationPassword)) {
            throw new IllegalArgumentException("Passwords must be same!");
        }

        userManager.createUser(new org.springframework.security.core.userdetails.User(
                email,
                pwEncoder.encode(password),
                AuthorityUtils.createAuthorityList("USER_ROLE")));

        return repository.findByEmail(email);
    }

    private boolean isEmailExists(String email) {
        User user = repository.findByEmail(email);

        if (user == null) {
            return false;
        }

        return true;
    }
}
