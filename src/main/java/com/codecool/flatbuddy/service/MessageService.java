package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidContentException;
import com.codecool.flatbuddy.exception.InvalidSubjectException;
import com.codecool.flatbuddy.model.Message;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class MessageService {

    @Autowired
    MessageRepository msgRepository;

    @Autowired
    UserService userService;

    public List<Message> getReceivedMessages() {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        return msgRepository.findAllByReceiverId(loggedInUser.getId());
    }

    public List<Message> getSentMessages() {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        return msgRepository.findAllBySenderId(loggedInUser.getId());
    }

    public Message getMessageById(int messageId) {
        return msgRepository.findById(messageId).get();
    }

    public void sendMessage(int receiverId, String subject, String content) throws InvalidSubjectException, InvalidContentException {

        if (subject == null || subject.equals("")) {
            throw new InvalidSubjectException("Subject cannot be empty!");
        }

        if (content == null || content.equals("")) {
            throw new InvalidContentException("Message content cannot be empty!");
        }

        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Message newMessage = new Message();
        newMessage.setSenderId(loggedInUser.getId());
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
