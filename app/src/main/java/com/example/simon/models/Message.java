package com.example.simon.models;

import java.util.Date;

public class Message {
    //Fields
    private int messageId;
    private String messageContent;
    private String sent;
    private User user;

    // Constructors

    public Message() {
    }

    public Message(String messageContent, String sent, User user) {
        this.messageContent = messageContent;
        this.sent = sent;
        this.user = user;
    }

    public Message(int messageId, String messageContent, String sent, User user) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.sent = sent;
        this.user = user;
    }

    // Getters and setters

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public com.example.simon.models.User getUser() {
        return user;
    }

    public void setUser(com.example.simon.models.User user) {
        this.user = user;
    }

}
