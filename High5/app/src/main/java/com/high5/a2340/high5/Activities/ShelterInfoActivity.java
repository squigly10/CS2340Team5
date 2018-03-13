package com.high5.a2340.high5.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.high5.a2340.high5.Model.Shelter;
import com.high5.a2340.high5.Model.AgeRange;
import com.high5.a2340.high5.Model.User;
import com.high5.a2340.high5.R;

import org.w3c.dom.Text;

/**
 * Created by henri on 2/27/18.
 */

public class ShelterInfoActivity extends AppCompatActivity implements View.OnClickListener  {

    private DatabaseReference userDbReference;
    private DatabaseReference shelterDbReference;
    private String userID;
    private User currentUser;


    private TextView nameField;
    private TextView capacityField;
    private TextView addressField;
    private TextView latitudeField;
    private TextView longitudeField;
    private TextView phoneNumberField;
    private TextView genderField;
    private TextView currentAvailabilityCount;
    private Spinner numberOfBedsSpinner;
    private Button reserveBedButton;

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
    private long currentAvailability;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_info);

        currentShelter = (Shelter) getIntent().getExtras().getSerializable("shelter");

        //Shelter fields
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

        //Widgets
        nameField = findViewById(R.id.textViewName);
        capacityField = findViewById(R.id.textViewCapacity);
        addressField = findViewById(R.id.textViewAddress);
        latitudeField = findViewById(R.id.textViewLat);
        longitudeField = findViewById(R.id.textViewLong);
        phoneNumberField = findViewById(R.id.textViewPhone);
        genderField = findViewById(R.id.textViewGender);
        currentAvailabilityCount = findViewById(R.id.currentAvailabilityCount);

        reserveBedButton = findViewById(R.id.reserveBedButton);
        reserveBedButton.setOnClickListener(this);

        numberOfBedsSpinner = findViewById(R.id.numberOfBedsSpinner);
        Integer[] bedNumberArray = {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> bedAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, bedNumberArray);
        bedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfBedsSpinner.setAdapter(bedAdapter);

        //Database
        shelterDbReference = FirebaseDatabase.getInstance().getReference("shelter-data/" + currentShelter.getUniqueKey());
        userID = FirebaseAuth.getInstance().getUid();
        userDbReference = FirebaseDatabase.getInstance().getReference("users/" + userID);
        //Get user Info
        userDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        nameField.setText(shelterName);
        capacityField.setText(capacity);
        addressField.setText(address);
        latitudeField.setText(latitude.toString());
        longitudeField.setText(longitude.toString());
        phoneNumberField.setText(phoneNumber);
        genderField.setText(restrictions);
        if (currentAvailability >= 0) {
            currentAvailabilityCount.setText(currentAvailability + "");
        } else {
            currentAvailabilityCount.setText("Please Contact Shelter");
        }

    }

    protected void onResume() {
        super.onResume();
        setContentView(R.layout.shelter_info);

        currentShelter = (Shelter) getIntent().getExtras().getSerializable("shelter");

        //Shelter fields
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

        //Widgets
        nameField = findViewById(R.id.textViewName);
        capacityField = findViewById(R.id.textViewCapacity);
        addressField = findViewById(R.id.textViewAddress);
        latitudeField = findViewById(R.id.textViewLat);
        longitudeField = findViewById(R.id.textViewLong);
        phoneNumberField = findViewById(R.id.textViewPhone);
        genderField = findViewById(R.id.textViewGender);
        currentAvailabilityCount = findViewById(R.id.currentAvailabilityCount);

        reserveBedButton = findViewById(R.id.reserveBedButton);
        reserveBedButton.setOnClickListener(this);

        numberOfBedsSpinner = findViewById(R.id.numberOfBedsSpinner);
        Integer[] bedNumberArray = {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> bedAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, bedNumberArray);
        bedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfBedsSpinner.setAdapter(bedAdapter);

        //Database
        shelterDbReference = FirebaseDatabase.getInstance().getReference("shelter-data/" + currentShelter.getUniqueKey());
        userID = FirebaseAuth.getInstance().getUid();
        userDbReference = FirebaseDatabase.getInstance().getReference("users/" + userID);
        //Get user Info
        userDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        nameField.setText(shelterName);
        capacityField.setText(capacity);
        addressField.setText(address);
        latitudeField.setText(latitude.toString());
        longitudeField.setText(longitude.toString());
        phoneNumberField.setText(phoneNumber);
        genderField.setText(restrictions);
        if (currentAvailability >= 0) {
            currentAvailabilityCount.setText(currentAvailability + "");
        } else {
            currentAvailabilityCount.setText("Please Contact Shelter");
        }
    }

    public void onClick(View view) {
        if (view == reserveBedButton) {
            createReservation();
        }
    }

    private void createReservation() {
        int numberOfBedSpots;
        if (currentUser.isHasReservation()) {
            Toast.makeText(ShelterInfoActivity.this, "Please cancel other reservations, and try again.",
                    Toast.LENGTH_LONG).show();

        } else if (currentShelter.getCurrentAvailability() == -1) {
            Toast.makeText(ShelterInfoActivity.this, "Please contact shelter for reservation information.",
                    Toast.LENGTH_LONG).show();
        } else {
            numberOfBedSpots = Integer.parseInt(numberOfBedsSpinner.getSelectedItem().toString());
            if (currentAvailability - numberOfBedSpots < 0) {
                Toast.makeText(ShelterInfoActivity.this, "The shelter doesn't have enough bed spots.",
                        Toast.LENGTH_LONG).show();
            } else {
                currentAvailability = currentAvailability - numberOfBedSpots;
                currentUser.setHasReservation(true);
                userDbReference.child("hasReservation").setValue(currentUser.isHasReservation());
                userDbReference.child("reservation-info").child("shelter-key").setValue(currentShelter.getUniqueKey());
                userDbReference.child("reservation-info").child("beds-reserved").setValue(numberOfBedSpots);
                currentShelter.setCurrentAvailability(currentAvailability);
                shelterDbReference.child("currentAvailability").setValue(currentShelter.getCurrentAvailability());
                currentAvailabilityCount.setText(currentAvailability + "");
                Toast.makeText(ShelterInfoActivity.this, "Reservation Successful",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}
