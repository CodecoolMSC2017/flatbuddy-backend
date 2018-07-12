package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findAllByisFlatmate(boolean flatmate);
}
