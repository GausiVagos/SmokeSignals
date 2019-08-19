package com.example.simon.models;

import com.google.gson.Gson;

import java.util.Date;

public class User
{
    // Fields
    private int userId;
    private String userName;
    private String password;
    private char gender;
    private String city;
    private double lC_Latitude;
    private double lC_Longitude;

    //Constructors
    public User(){}

    public User(String userName, String password, double LC_Latitude, double LC_Longitude) {
        this.userName = userName;
        this.password = password;
        this.lC_Latitude = LC_Latitude;
        this.lC_Longitude = LC_Longitude;
    }

    public User(String userName, String password, char gender, String city, double LC_Latitude, double LC_Longitude)
    {
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.city = city;
        this.lC_Latitude = LC_Latitude;
        this.lC_Longitude = LC_Longitude;
    }

    public User(int userId, String userName, String password, char gender, String city, double LC_Latitude, double LC_Longitude) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.city = city;
        this.lC_Latitude = LC_Latitude;
        this.lC_Longitude = LC_Longitude;
    }

    //Getters

    public int getUserId()
    {
        return userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public char getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public double getLC_Latitude() {
        return lC_Latitude;
    }

    public double getLC_Longitude() {
        return lC_Longitude;
    }

    //Setters

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(char gender) { this.gender = gender; }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLC_Latitude(double LC_Latitude) {
        this.lC_Latitude = LC_Latitude;
    }

    public void setLC_Longitude(double LC_Longitude) { this.lC_Longitude = LC_Longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                userName.equals(user.userName);
    }

    @Override
    public String toString()
    {
        return new Gson().toJson(this);
    }
}
