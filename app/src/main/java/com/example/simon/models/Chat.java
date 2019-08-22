package com.example.simon.models;

import android.content.Context;

import com.example.simon.smokesignals.R;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class Chat
{
    //Fields
    private int chatId;
    private String subject;
    private Set<User> users;
    private Set<Message> messages;

    //Constructors

    public Chat() {
    }

    public Chat(String subject, Set<User> users) {
        this.subject = subject;
        this.users = users;
        messages = new HashSet<>();
    }

    public Chat(int chatId, String subject, Set<User> users, Set<Message> messages) {
        this.chatId = chatId;
        this.subject = subject;
        this.users = users;
        this.messages = messages;
    }

    // Setters and getters


    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /*
    public String userString(User you, Context context)
    {
        String[] names = new String[getUsers().size()];
        for(int i=0; i<getUsers().size(); i++)
        {
            names[i] = ((User)getUsers().toArray()[i]).getUserName();
        }
        String us = "";

        for(int i =0; i<names.length;i++)
        {
            String name = names[i].equals(you.getUserName()) ? context.getString(R.string.you) : names[i];
            us+=name += i<=names.length-2 ? ", " : i==names.length-1 ? context.getString(R.string.and) : ".";
        }


        return us;
    }
    */
}
