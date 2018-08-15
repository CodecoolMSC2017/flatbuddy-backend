package com.codecool.flatbuddy.controller;


import com.codecool.flatbuddy.exception.*;
import com.codecool.flatbuddy.model.Message;
import com.codecool.flatbuddy.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class RestMessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/new")
    public void sendNewMessage(@RequestBody Map<String, Object> map) throws InvalidContentException, InvalidSubjectException, InvalidMessageSendingException, NotAbleToSendMessageException {
        String receiverId = String.valueOf(map.get("receiverId"));
        String subject = (String) map.get("subject");
        String content = (String) map.get("content");

        messageService.sendMessage(receiverId, subject, content);
    }

    @GetMapping(path = "/received", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> receivedMessages() throws NoMessagesException {
        return messageService.getReceivedMessages();
    }

    @GetMapping(path = "/sent", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> sentMessages() throws NoMessagesException {
        return messageService.getSentMessages();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message getMessageById(@PathVariable("id") String messageId) throws InvalidMessageIdException, InvalidMessageAccessException, DeletedMessageException {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessageById(@PathVariable("id") String messageId) throws InvalidMessageIdException, InvalidMessageAccessException {
        messageService.deleteMessage(messageId);
    }
}
