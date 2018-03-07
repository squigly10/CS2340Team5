package com.high5.a2340.high5.Model;

/**
 * Created by hsmul on 3/7/2018.
 */

public enum AgeRange {
    NEWBORNS("Families with newborns"),
    CHILDREN("Children"),
    YOUNGADULTS("Young Adults"),
    ANYONE("Anyone");

    String value;

    AgeRange(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
