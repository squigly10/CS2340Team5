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


    /**
     * Constructor to create a new shelter instance
     * @param address               The shelter's address
     * @param capacity              The shelter's total capacity
     * @param latitude              The latitude coordinate of the shelter
     * @param longitude             The logitudinal coordinate of the shelter
     * @param phoneNumber           The shelter's phone number
     * @param restrictions          Shelter Restrictions
     * @param shelterName           The shelter's name
     * @param isFemale              If the shelter allows females
     * @param isMale                If the shelter allows males
     * @param specialNotes          Any notes about the shelter
     * @param shelterAgeRange       The shelter's age range
     * @param shelterID             Shelter Unique Identifier
     * @param currentAvailability   The current availability of the shelter
     */
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

    /**
     * Address Getter
     * @return The shelter's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Capacity Getter
     * @return The shelter's capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Latitude Getter
     * @return The shelter's latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Longitude Getter
     * @return The shelter's longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Phone Number Getter
     * @return The shelter's phonenumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Restriction Getter
     * @return Any restrictions the shelter has
     */
    public String getRestrictions() {
        return restrictions;
    }

    /**
     * Shelter Name Getter
     * @return The shelter's name
     */
    public String getShelterName() {
        return shelterName;
    }

    /**
     * Checks if the shelter allows females
     * @return whether or not the shelter allows females
     */
    public boolean isFemale() { return isFemale; }

    /**
     * Checks if the shelter allows males
     * @return whether or not the shelter allows males
     */
    public boolean isMale() { return isMale; }

    /**
     * Special Notes Getter
     * @return Any special notes about the shelter
     */
    public String getSpecialNotes() {
        return specialNotes;
    }

    /**
     * Age Range Getter
     * @return The shelter's Age Range
     */
    public AgeRange getAgeRange() { return shelterAgeRange; }

    /**
     * Returns the shelter as a string
     * @return  the name of the shelter
     */
    public String toString() {return shelterName; }

    /**
     * Current Availability Getter
     * @return The shelter's current availability
     */
    public int getCurrentAvailability() {
        return currentAvailability;
    }

    /**
     * Shelter ID Getter
     * @return The shelter's unique shelter ID
     */
    public String getShelterID() {return shelterID; }

    /**
     * Current Availability setter
     * @param currentAvailability   the new availability for the shelter
     */
    public void setCurrentAvailability(int currentAvailability) {
        this.currentAvailability = currentAvailability;
    }
}
