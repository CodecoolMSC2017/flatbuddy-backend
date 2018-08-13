package com.codecool.flatbuddy.controller;

import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.Notification;
import com.codecool.flatbuddy.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestNotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(path = "/user/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Notification> getAllNotifications() {
        return notificationService.getNotifications();
    }

    @PutMapping(path = "/user/{userId}/notificationseen/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void setNotificationSeen(@PathVariable("id")int id,@PathVariable("userId")int userId) throws UnauthorizedException {
        notificationService.setNotificationSeenById(id,userId);
    }

    @DeleteMapping(path = "/user/{userId}/deletenotification/{id}")
    public void deleteMatch(@PathVariable("id")int id,@PathVariable("userId")int userId) throws UnauthorizedException {
        notificationService.deleteNotificationById(id,userId);
    }

}
