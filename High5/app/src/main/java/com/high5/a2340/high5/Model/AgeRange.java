package com.high5.a2340.high5.Model;

/**
 * Written by Henri Smulders
 * 02/18
 */

public enum AgeRange {
    ANYONE("Anyone"),
    NEWBORNS("Families with newborns"),
    CHILDREN("Children"),
    WOMENANDCHILDREN("Women/Children"),
    YOUNGADULTS("Young Adults"),
    FAMILIES("Families");

    final String value;
    AgeRange(String value) {
        this.value = value;
    }


    public String toString() {
        return value;
    }
}
