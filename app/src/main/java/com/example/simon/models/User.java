package com.example.simon.models;

import java.util.Date;

public class User
{
    // Fields
    private int UserId;
    private String UserName;
    private String Password;
    private String Gender;
    private String City;
    private Date BirthDay;
    private String LC_Latitude;
    private String LC_Longitude;

    //Constructors
    public User(){}

    public User(String userName, String password, String LC_Latitude, String LC_Longitude) {
        UserName = userName;
        Password = password;
        this.LC_Latitude = LC_Latitude;
        this.LC_Longitude = LC_Longitude;
    }

    public User(int userId, String userName, String password, String gender, String city, Date birthDay, String LC_Latitude, String LC_Longitude) {
        UserId = userId;
        UserName = userName;
        Password = password;
        Gender = gender;
        City = city;
        BirthDay = birthDay;
        this.LC_Latitude = LC_Latitude;
        this.LC_Longitude = LC_Longitude;
    }

    //Getters

    public int getUserId()
    {
        return UserId;
    }

    public String getUserName()
    {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public String getGender() {
        return Gender;
    }

    public String getCity() {
        return City;
    }

    public Date getBirthDay() {
        return BirthDay;
    }

    public String getLC_Latitude() {
        return LC_Latitude;
    }

    public String getLC_Longitude() {
        return LC_Longitude;
    }

    //Setters

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setBirthDay(Date birthDay) {
        BirthDay = birthDay;
    }

    public void setLC_Latitude(String LC_Latitude) {
        this.LC_Latitude = LC_Latitude;
    }

    public void setLC_Longitude(String LC_Longitude) {
        this.LC_Longitude = LC_Longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return UserId == user.UserId &&
                UserName.equals(user.UserName);
    }

}
