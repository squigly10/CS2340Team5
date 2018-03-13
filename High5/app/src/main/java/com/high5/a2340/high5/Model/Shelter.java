package com.high5.a2340.high5.Model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by david on 2/26/2018.
 */

public class Shelter implements Serializable {
    private String address;
    private String capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String restrictions;
    private String shelterName;
    private boolean isFemale;
    private boolean isMale;
    private String specialNotes;
    private long currentAvailability;
    private AgeRange shelterAgeRange;
    private long uniqueKey;


    public Shelter() {

    }

    public Shelter(String address, String capacity, double latitude, double longitude,
                   String phoneNumber, String restrictions, String shelterName, boolean isFemale,
                   boolean isMale, String specialNotes, AgeRange shelterAgeRange, long uniqueKey, long currentAvailability) {
        this.address = address;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;
        this.shelterName = shelterName;
        this.isFemale = isFemale;
        this.isMale = isMale;
        this.specialNotes = specialNotes;
        this.shelterAgeRange = shelterAgeRange;
        this.currentAvailability = currentAvailability;
        this.uniqueKey = uniqueKey;

    }

    public String getAddress() {
        return address;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getShelterName() {
        return shelterName;
    }

    public boolean isFemale() { return isFemale; }

    public void setFemale(boolean female) { isFemale = female; }

    public boolean isMale() { return isMale; }

    public void setMale(boolean male) { isMale = male; }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public AgeRange getAgeRange() { return shelterAgeRange; }

    public void setAgeRange(AgeRange age) { shelterAgeRange = age; }

    public String toString() {return shelterName; }

    public Long getCurrentAvailability() {
        return currentAvailability;
    }

    public void setCurrentAvailability(Long currentAvailability) {
        this.currentAvailability = currentAvailability;
    }

    public Long getUniqueKey() {
        return uniqueKey;
    }
}
