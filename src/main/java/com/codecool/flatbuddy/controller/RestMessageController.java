package com.codecool.flatbuddy.controller;


import com.codecool.flatbuddy.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class RestMessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/new")
    public void sendNewMessage(@RequestBody Map<String, Object> map) {
        Integer senderId = (Integer) map.get("senderId");
        Integer receiverId = (Integer) map.get("receiverId");
        String subject = (String) map.get("subject");
        String content = (String) map.get("content");

        messageService.sendMessage(senderId, receiverId, subject, content);
    }

    @GetMapping(path = "/received", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> receivedMessages() {
        return messageService.getReceivedMessages();
    }

    @GetMapping(path = "/sent", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> sentMessages() {
        return messageService.getSentMessages();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message getMessageById(@PathVariable("id") int messageId) {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessageById(@PathVariable("id") int messageId) {
        messageService.deleteMessage(messageId);
    }
}