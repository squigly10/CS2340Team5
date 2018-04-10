package com.high5.a2340.high5.Model;

import java.io.Serializable;

/**
 * Created by David
 * 2/26/2018
 */

public class Shelter implements Serializable {
    private final String address;
    private final String capacity;
    private final double latitude;
    private final double longitude;
    private final String phoneNumber;
    private final String restrictions;
    private final String shelterName;
    private final boolean isFemale;
    private final boolean isMale;
    private final String specialNotes;
    private Integer currentAvailability;
    private final AgeRange shelterAgeRange;
    private final String shelterID;


    public Shelter(String address, String capacity, double latitude, double longitude,
                   String phoneNumber, String restrictions, String shelterName, boolean isFemale,
                   boolean isMale, String specialNotes, AgeRange shelterAgeRange, String shelterID,
                   int currentAvailability) {
        this.address = address;
        this.capacity = capacity;
        this.currentAvailability = currentAvailability;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;
        this.shelterName = shelterName;
        this.isFemale = isFemale;
        this.isMale = isMale;
        this.specialNotes = specialNotes;
        this.shelterAgeRange = shelterAgeRange;
        this.shelterID = shelterID;

    }

    public String getAddress() {
        return address;
    }

    public String getCapacity() {
        return capacity;
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

    public String getRestrictions() {
        return restrictions;
    }

    public String getShelterName() {
        return shelterName;
    }

    public boolean isFemale() { return isFemale; }

    public boolean isMale() { return isMale; }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public AgeRange getAgeRange() { return shelterAgeRange; }

    public String toString() {return shelterName; }

    public int getCurrentAvailability() {
        return currentAvailability;
    }

    public String getShelterID() {return shelterID; }

    public void setCurrentAvailability(int currentAvailability) {
        this.currentAvailability = currentAvailability;
    }
}
