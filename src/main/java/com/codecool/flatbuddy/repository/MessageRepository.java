package com.codecool.flatbuddy.repository;

import com.codecool.flatbuddy.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findAllBySenderId(int id);
    List<Message> findAllByReceiverId(int id);
    List<Message> findAllBySenderIdOrderByDateDesc(int id);
    List<Message> findAllByReceiverIdOrderByDateDesc(int id);

}
