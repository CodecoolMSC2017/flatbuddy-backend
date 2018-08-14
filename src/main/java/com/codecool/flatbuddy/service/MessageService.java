package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.*;
import com.codecool.flatbuddy.model.Message;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class MessageService {

    @Autowired
    private MessageRepository msgRepository;

    @Autowired
    private UserService userService;

    public List<Message> getReceivedMessages() throws NoMessagesException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Message> allMessages = msgRepository.findAllByReceiverId(loggedInUser.getId());
        List<Message> visibleMessages = checkIsEnabled(allMessages);

        if (visibleMessages.isEmpty()) {
            throw new NoMessagesException();
        } else {
            return visibleMessages;
        }
    }

    public List<Message> getSentMessages() throws NoMessagesException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Message> allMessages = msgRepository.findAllBySenderId(loggedInUser.getId());
        List<Message> visibleMessages = checkIsEnabled(allMessages);

        if (visibleMessages.isEmpty()) {
            throw new NoMessagesException();
        } else {
            return visibleMessages;
        }

    }

    public Message getMessageById(String messageId) throws InvalidMessageIdException, InvalidMessageAccessException, DeletedMessageException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        try {
            Message message = msgRepository.findById(Integer.valueOf(messageId)).get();
            if (message.getReceiverId() == loggedInUser.getId() || message.getSenderId() == loggedInUser.getId()) {
                if (message.isEnabledToReceiver() && message.isEnabledToSender()) {
                    return message;
                } else if (loggedInUser.getId() == message.getSenderId() && message.isEnabledToSender()) {
                    return message;
                } else if (loggedInUser.getId() == message.getReceiverId() && message.isEnabledToReceiver()) {
                    return message;
                } else {
                    throw new DeletedMessageException();
                }
            } else {
                throw new InvalidMessageAccessException();
            }
        } catch (NoSuchElementException | NumberFormatException ex) {
            throw new InvalidMessageIdException();
        }
    }



    public void sendMessage(String receiverId, String subject, String content) throws InvalidSubjectException, InvalidContentException, InvalidMessageSendingException {

        if (subject == null || subject.equals("")) {
            throw new InvalidSubjectException();
        }

        if (content == null || content.equals("")) {
            throw new InvalidContentException();
        }

        try {
            User user = userService.getUserById(Integer.valueOf(receiverId));
        } catch (NumberFormatException | NoSuchElementException ex) {
            throw new InvalidMessageSendingException();
        }

        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Message newMessage = new Message();
        newMessage.setSenderId(loggedInUser.getId());
        newMessage.setReceiverId(Integer.valueOf(receiverId));
        newMessage.setContent(content);
        newMessage.setSubject(subject);
        newMessage.setEnabledToSender(true);
        newMessage.setEnabledToReceiver(true);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.format(currentDate);

        newMessage.setDate(currentDate);

        msgRepository.save(newMessage);
    }

    public void deleteMessage(String messageId) throws InvalidMessageIdException, InvalidMessageAccessException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            Message msg = msgRepository.findById(Integer.valueOf(messageId)).get();
            if (loggedInUser.getId() == msg.getReceiverId()) {
                msg.setEnabledToReceiver(false);
            } else if (loggedInUser.getId() == msg.getSenderId()) {
                msg.setEnabledToSender(false);
            } else {
                throw new InvalidMessageAccessException();
            }
            msgRepository.save(msg);
        } catch (EmptyResultDataAccessException | NumberFormatException | NoSuchElementException ex) {
            throw new InvalidMessageIdException();
        }
    }

    private List<Message> checkIsEnabled(List<Message> messages) {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Message> visibleMessages = new ArrayList<Message>();
        for (int i = 0; i < messages.size() ; i++) {
            Message msg = messages.get(i);
            if (msg.isEnabledToSender() && msg.getSenderId() == loggedInUser.getId()) {
                visibleMessages.add(msg);
            } else if (msg.isEnabledToReceiver() && msg.getReceiverId() == loggedInUser.getId()) {
                visibleMessages.add(msg);
            }
        }
        return visibleMessages;
    }
}
