package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidContentException;
import com.codecool.flatbuddy.exception.InvalidSubjectException;
import com.codecool.flatbuddy.model.Message;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.MessageRepository;
import com.codecool.flatbuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageService {

    @Autowired
    MessageRepository msgRepository;

    @Autowired
    UserService userService;

    User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    public List<Message> getReceivedMessages() {
        return msgRepository.findAllByReceiverId(loggedInUser.getId());
    }

    public List<Message> getSentMessages() {
        return msgRepository.findAllBySenderId(loggedInUser.getId());
    }

    public Message getMessageById(int messageId) {
        return msgRepository.findById(messageId).get();
    }

    public void sendMessage(int senderId, int receiverId, String subject, String content) throws InvalidSubjectException, InvalidContentException {

        if (subject == null || subject.equals("")) {
            throw new InvalidSubjectException("Subject cannot be empty!");
        }

        if (content == null || content.equals("")) {
            throw new InvalidContentException("Message content cannot be empty!");
        }

        Message newMessage = new Message();
        newMessage.setSenderId(senderId);
        newMessage.setReceiverId(receiverId);
        newMessage.setContent(content);
        newMessage.setSubject(subject);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.format(currentDate);

        newMessage.setDate(currentDate);

        msgRepository.save(newMessage);
    }

    public void deleteMessage(int messageId){
        msgRepository.deleteById(messageId);
    }
}
