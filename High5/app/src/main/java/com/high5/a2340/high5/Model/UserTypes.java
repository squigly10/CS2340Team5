package com.high5.a2340.high5.Model;

/**
 * Created by david on 2/19/2018.
 */

public enum UserTypes {
    USER("User"),
    ADMIN("Administrator"),
    EMPLOYEE("Employee");

    String value;

    UserTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
