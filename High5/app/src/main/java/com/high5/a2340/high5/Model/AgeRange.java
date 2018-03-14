package com.high5.a2340.high5.Model;

/**
 * Created by hsmul on 3/7/2018.
 */

public enum AgeRange {
    ANYONE("Anyone"),
    NEWBORNS("Families with newborns"),
    CHILDREN("Children"),
    YOUNGADULTS("Young Adults"),
    FAMILIES("Families");

    String value;
    AgeRange(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return value;
    }
}
