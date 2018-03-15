package com.high5.a2340.high5.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.high5.a2340.high5.Model.Shelter;
import com.high5.a2340.high5.Model.AgeRange;
import com.high5.a2340.high5.R;

/**
 * Created by henri on 2/27/18.
 */

public class ShelterInfoActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {


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
    private String shelterID;

    private Button reserveButton;
    private Spinner reserveSpinner;
    private Double numberOfSpaces = 0.0;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

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
        shelterID = currentShelter.getShelterID();

        nameField = (TextView) findViewById(R.id.textViewName);
        capacityField = (TextView) findViewById(R.id.textViewCapacity);
        addressField = (TextView) findViewById(R.id.textViewAddress);
        latitudeField = (TextView) findViewById(R.id.textViewLat);
        longitudeField = (TextView) findViewById(R.id.textViewLong);
        phoneNumberField = (TextView) findViewById(R.id.textViewPhone);
        restrictionsField = (TextView) findViewById(R.id.textViewRestrictions);
        ageRangeField = (TextView) findViewById(R.id.textViewAgeRange);
        genderField = (TextView) findViewById(R.id.textViewGender);
        specialNotesField = (TextView) findViewById(R.id.textViewSpecialNotes);
        currentAvailabilityCount = (TextView) findViewById(R.id.textViewCurrentAvailability);

        reserveSpinner = (Spinner) findViewById(R.id.reserveSpinner);
        reserveButton = (Button) findViewById(R.id.reserveButton);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reserveSpotArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reserveSpinner.setAdapter(adapter);

        reserveSpinner.setOnItemSelectedListener(this);

        reserveButton.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        if (v == reserveButton) {
            if (numberOfSpaces != 0.0) {
                reserveSpot();
            } else {
                Toast.makeText(this,
                        "Please select a number of spots for your reservation",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void reserveSpot() {
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("reservations")
                        .hasChild(userID)) {

                    Double availability = Double.valueOf(dataSnapshot.child("shelter-data").
                            child(shelterID).child("currentAvailability").getValue().toString());
                    if (availability >= numberOfSpaces) {
                        mDatabase.child("shelter-data").
                                child(shelterID).child("currentAvailability").
                                setValue(availability - numberOfSpaces);
                        mDatabase.child("reservations").child(userID)
                                .child("numberOfSpots").setValue(numberOfSpaces);
                        mDatabase.child("reservations").child(userID)
                                .child("location").setValue(shelterName);
                        mDatabase.child("reservations").child(userID)
                                .child("ShelterID").setValue(shelterID);
                        Toast.makeText(ShelterInfoActivity.this, "Reservation Successful",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ShelterInfoActivity.this,
                                "There is not enough room at this Shelter",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String location = (String) dataSnapshot.child("reservations")
                            .child(userID).child("location").getValue();
                    Double currentReservation = Double.valueOf(dataSnapshot.child("reservations")
                            .child(userID).child("numberOfSpots").getValue().toString());
                    String displayString = "You currently have a reservation at " + location
                            + " for "
                            + currentReservation.intValue() + " spots.";
                    Toast.makeText(ShelterInfoActivity.this, displayString,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String temp = parent.getItemAtPosition(position).toString();
        if (temp.equals("-")) {
            numberOfSpaces = 0.0;
        } else {
            numberOfSpaces = Double.valueOf(temp);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
