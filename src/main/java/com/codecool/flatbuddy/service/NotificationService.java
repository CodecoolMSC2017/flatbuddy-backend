package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.Notification;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.repository.NotificationRepository;
import com.codecool.flatbuddy.repository.RentSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    public List<Notification> getNotifications() {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return notificationRepository.findAllByReceiverId(loggedInUser.getId());
    }

    public void setNotificationSeenById(int id,int userId) throws UnauthorizedException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedInUser.getId() == userId) {
            Notification notification = notificationRepository.findById(id).get();
            notification.setSeen(true);
            notificationRepository.save(notification);
        }
        else {
            throw new UnauthorizedException("Acces denied.This isn't your notification.");
        }
    }

    public void deleteNotificationById(int id,int userId) throws UnauthorizedException {
        User loggedInUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (loggedInUser.getId() == userId) {
            Notification notification = notificationRepository.findById(id).get();
            notificationRepository.delete(notification);
        }
        else {
            throw new UnauthorizedException("Acces denied.You can't delete another user's notification.");
        }
    }

    public void createNotification(int receiverId,String content,String type,Integer idOfSubject) {
        Notification notification = new Notification();
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        sdf.format(currentDate);

        notification.setReceiverId(receiverId);
        notification.setContent(content);
        notification.setSeen(false);
        notification.setDate(currentDate);
        notification.setType(type);
        notification.setIdOfSubject(idOfSubject);
        notificationRepository.save(notification);
    }
}
