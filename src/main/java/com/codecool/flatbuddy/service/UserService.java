package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.UserNotFoundException;
import com.codecool.flatbuddy.model.RentAd;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.UserRepository;
import com.codecool.flatbuddy.util.DisabilityChecker;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public final class UserService {

    HttpTransport transport = new NetHttpTransport();
    JsonFactory jsonFactory = new JacksonFactory();

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

    public User getGoogleAuthenticatedUser(String email) {
        if (!isEmailExists(email)) {
            userManager.createUser(new org.springframework.security.core.userdetails.User(
                    email, "",
                    AuthorityUtils.createAuthorityList("USER_ROLE")));
        }
        return getUserByEmail(email);
    }

    private boolean isEmailExists(String email) {
        User user = repository.findByEmail(email);

        if (user == null) {
            return false;
        }

        return true;
    }

    public User getUserByToken(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).setAudience(Collections.singletonList("87138340487-dna6m556e14folussr8dqq5pn0k3em07.apps.googleusercontent.com")).build();

        GoogleIdToken token = verifier.verify(idToken);

        if (token != null) {
            GoogleIdToken.Payload payload = token.getPayload();
            String userId = payload.getSubject();

            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            User user = getGoogleAuthenticatedUser(email);

            List<GrantedAuthority> roles = user.getAuthorities()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null, roles);

            SecurityContextHolder.getContext().setAuthentication(auth);

            return user;
        } else {
            System.out.println("Invalid ID token.");
            return null;
        }
    }
}
