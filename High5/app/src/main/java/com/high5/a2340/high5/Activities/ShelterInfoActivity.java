package com.high5.a2340.high5.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.high5.a2340.high5.Model.Shelter;
import com.high5.a2340.high5.Model.AgeRange;
import com.high5.a2340.high5.R;

/**
 * Created by henri on 2/27/18.
 */

public class ShelterInfoActivity extends AppCompatActivity  {


    private TextView nameField;
    private TextView capacityField;
    private TextView addressField;
    private TextView latitudeField;
    private TextView longitudeField;
    private TextView phoneNumberField;
    private TextView restrictionsField;
    private TextView ageRangeField;
    private TextView genderField;
    private TextView currentAvailabilityCount;
    private TextView specialNotesField;

    private Shelter currentShelter;

    private String address;
    private String capacity;
    private Double latitude;
    private Double longitude;
    private String phoneNumber;
    private String restrictions;
    private String shelterName;
    private boolean isFemale;
    private boolean isMale;
    private String specialNotes;
    private AgeRange shelterAgeRange;
    private Integer currentAvailability;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_info);

        currentShelter = (Shelter) getIntent().getExtras().getSerializable("shelter");

        address = currentShelter.getAddress();
        capacity = currentShelter.getCapacity();
        shelterName = currentShelter.getShelterName();
        latitude = currentShelter.getLatitude();
        longitude = currentShelter.getLongitude();
        phoneNumber = currentShelter.getPhoneNumber();
        restrictions = currentShelter.getRestrictions();
        isFemale = currentShelter.isFemale();
        isMale = currentShelter.isMale();
        specialNotes = currentShelter.getSpecialNotes();
        shelterAgeRange = currentShelter.getAgeRange();
        currentAvailability = currentShelter.getCurrentAvailability();

        nameField = (TextView) findViewById(R.id.textViewName);
        capacityField = (TextView) findViewById(R.id.textViewCapacity);
        addressField = (TextView) findViewById(R.id.textViewAddress);
        latitudeField = (TextView) findViewById(R.id.textViewLat);
        longitudeField = (TextView) findViewById(R.id.textViewLong);
        phoneNumberField = (TextView) findViewById(R.id.textViewPhone);
        restrictionsField = (TextView) findViewById(R.id.textViewRestrictions);
        ageRangeField = (TextView) findViewById(R.id.textViewAgeRange) ;
        genderField = (TextView) findViewById(R.id.textViewGender);
        specialNotesField = (TextView) findViewById(R.id.textViewSpecialNotes);
        currentAvailabilityCount = (TextView) findViewById(R.id.textViewCurrentAvailability);



        nameField.setText(shelterName);
        capacityField.setText(capacity);
        addressField.setText(address);
        latitudeField.setText(latitude.toString());
        longitudeField.setText(longitude.toString());
        phoneNumber = String.valueOf(phoneNumber).replaceFirst("(\\d{3})(\\d{3})(\\d+)",
                "($1) $2-$3");
        phoneNumberField.setText(phoneNumber);
        restrictionsField.setText(restrictions);
        ageRangeField.setText(shelterAgeRange.toString());
        if (isFemale && isMale) {
            genderField.setText("Men and Women");
        } else if (isFemale) {
            genderField.setText("Women Only");
        } else if (isMale) {
            genderField.setText("Men Only");
        } else {
            genderField.setText("Anyone");
        }

        specialNotesField.setText(specialNotes);
        if (currentAvailability >= 0) {
            currentAvailabilityCount.setText(currentAvailability.toString());
        } else {
            currentAvailabilityCount.setText("Please Contact Shelter");
        }
    }



}
