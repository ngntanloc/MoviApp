package com.example.moviapp.Models;

import java.io.Serializable;

public class User implements Serializable {

    private String userID;
    private String userName;
    private String email;
    private String password;
    private String imgAvatar;
    private String phoneNumber;

    public User(String userName, String email, String password, String imgAvatar, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.imgAvatar = imgAvatar;
        this.phoneNumber = phoneNumber;
    }

    public User(String email) {
        this.userName = "User Name";
        this.email = email;
    }

    public User(String userID, String userName, String email) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
