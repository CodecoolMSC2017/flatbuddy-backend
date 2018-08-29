package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    @Query(value = "select * from users where id not in (Select users.id from users Join matches on users.id = matches.user_b where user_a =?1 or user_b =?1) and enabled = true and is_flatmate = true and id != ?1",nativeQuery = true)
    List<User> findPeople(int userId);

    List<User> findAllByisFlatmateAndEnabled(boolean flatmate, boolean enabled);
    @Query(value = "select * from users where enabled = true",nativeQuery = true)
    List<User> findAllPeople();

    @Query(value = "select u from User u join u.authorities a where :role in a")
    List<User> findAllAdmin(@Param("role") String role);
}
