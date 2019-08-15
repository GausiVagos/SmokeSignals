package com.example.simon.models;
import java.util.Date;
import java.util.Objects;

import com.example.simon.models.User;

public class Message {
    //Fields
    private int MessageId;
    private String MessageContent;
    private Date Sent;
    private User User;

    // Constructors

    public Message() {
    }

    public Message(String messageContent, Date sent, User user) {
        MessageContent = messageContent;
        Sent = sent;
        User = user;
    }

    public Message(int messageId, String messageContent, Date sent, User user) {
        MessageId = messageId;
        MessageContent = messageContent;
        Sent = sent;
        User = user;
    }

    // Getters and setters

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    public Date getSent() {
        return Sent;
    }

    public void setSent(Date sent) {
        Sent = sent;
    }

    public com.example.simon.models.User getUser() {
        return User;
    }

    public void setUser(com.example.simon.models.User user) {
        User = user;
    }

}
