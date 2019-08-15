package com.example.simon.models;
import com.example.simon.models.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Chat
{
    //Fields
    private int ChatId;
    private String Subject;
    private Set<User> Users;
    private Set<Message> Messages;

    //Constructors

    public Chat() {
    }

    public Chat(String subject, Set<User> users) {
        Subject = subject;
        Users = users;
        Messages = new HashSet<>();
    }

    public Chat(int chatId, String subject, Set<User> users, Set<Message> messages) {
        ChatId = chatId;
        Subject = subject;
        Users = users;
        Messages = messages;
    }

    // Setters and getters


    public int getChatId() {
        return ChatId;
    }

    public void setChatId(int chatId) {
        ChatId = chatId;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public Set<User> getUsers() {
        return Users;
    }

    public void setUsers(Set<User> users) {
        Users = users;
    }

    public Set<Message> getMessages() {
        return Messages;
    }

    public void setMessages(Set<Message> messages) {
        Messages = messages;
    }
}
