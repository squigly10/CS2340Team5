package com.high5.a2340.high5.Model;

import android.util.Log;

/**
 * Created by david on 2/26/2018.
 */

public class Shelter {
    private String address;
    private String capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String restrictions;
    private String shelterName;
    private String specialNotes;

    public Shelter() {

    }

    public Shelter(String address, String capacity, double latitude, double longitude, String phoneNumber,
                    String restrictions, String shelterName, String specialNotes) {
        this.address = address;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;
        this.shelterName = shelterName;
        this.specialNotes = specialNotes;

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

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }
}
