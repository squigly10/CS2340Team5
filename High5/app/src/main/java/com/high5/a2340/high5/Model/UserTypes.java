package com.high5.a2340.high5.Model;

/**
 *  An enum for types of users
 *  @author Tom
 *  @version 1.4
 */
public enum UserTypes {
    USER("User"),
    ADMIN("Administrator"),
    EMPLOYEE("Employee");

    final String value;

    UserTypes(String value) {
        this.value = value;
    }

    /**
     * Gets the current user type
     * @return  this.value      the current user type
     */
    public String getValue() {
        return this.value;
    }
}
