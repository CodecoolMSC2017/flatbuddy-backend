package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification,Integer>{
    List<Notification> findAllByReceiverId(int id);

}
