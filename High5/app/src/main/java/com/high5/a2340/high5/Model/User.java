package com.high5.a2340.high5.Model;


/**
 *  Object to hold data for individual users
 *  @author High5
 *  @version 1.4
 */
public class User {

    private String email;
    private String passWord;
    private String userType;
    private boolean hasReservation;

    /**
     * Creates a new user so that it can be added to the firebase database
     * @param email         the user's email
     * @param passWord      the user's password
     * @param userType      the type of user
     */
    public User(String email, String passWord, String userType) {
        this.email = email;
        this.passWord = passWord;
        this.userType = userType;
        this.hasReservation = false;
    }

}
