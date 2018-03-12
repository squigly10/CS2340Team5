package com.high5.a2340.high5.Model;

/**
 * Created by david on 2/19/2018.
 */

public class User {

    private String email;
    private String passWord;
    private String userType;
    private boolean hasReservation;

    public User(String email, String passWord, String userType) {
        this.email = email;
        this.passWord = passWord;
        this.userType = userType;
        this.hasReservation = false;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
