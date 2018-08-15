package com.codecool.flatbuddy.model;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "sender_id")
    private Integer senderId;
    @Column(name = "sender_name")
    private String senderName;
    @Column(name = "receiver_id")
    private Integer receiverId;
    @Column (name = "receiver_name")
    private String receiverName;
    private String subject;
    private String content;
    private Date date;
    @Column(name = "is_enabled_to_sender")
    private boolean isEnabledToSender;
    @Column(name = "is_enabled_to_receiver")
    private boolean isEnabledToReceiver;
    @Column(name = "is_seen")
    private boolean isSeen;

    public Message(){}

    public Integer getId() {
        return id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEnabledToSender() {
        return isEnabledToSender;
    }

    public void setEnabledToSender(boolean enabledToSender) {
        isEnabledToSender = enabledToSender;
    }

    public boolean isEnabledToReceiver() {
        return isEnabledToReceiver;
    }

    public void setEnabledToReceiver(boolean enabledToReceiver) {
        isEnabledToReceiver = enabledToReceiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
