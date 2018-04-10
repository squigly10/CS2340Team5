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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.high5.a2340.high5.Model.Shelter;
import com.high5.a2340.high5.R;

/**
 * Created by Henri
 * 2/27/18
 */

public class ShelterInfoActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private String shelterID;
    private String shelterName;

    private Button reserveButton;
    private Double numberOfSpaces = 0.0;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_info);

        Shelter currentShelter = (Shelter) getIntent().getExtras().getSerializable("shelter");

        String address = currentShelter.getAddress();
        String capacity = currentShelter.getCapacity();
        shelterName = currentShelter.getShelterName();
        Double latitude = currentShelter.getLatitude();
        Double longitude = currentShelter.getLongitude();
        String phoneNumber = currentShelter.getPhoneNumber();
        String restrictions = currentShelter.getRestrictions();
        boolean isFemale = currentShelter.isFemale();
        boolean isMale = currentShelter.isMale();
        String specialNotes = currentShelter.getSpecialNotes();
        String shelterAgeRange = currentShelter.getAgeRange().toString();
        Integer currentAvailability = currentShelter.getCurrentAvailability();
        shelterID = currentShelter.getShelterID();

        TextView nameField = findViewById(R.id.textViewName);
        TextView capacityField = findViewById(R.id.textViewCapacity);
        TextView addressField = findViewById(R.id.textViewAddress);
        TextView latitudeField = findViewById(R.id.textViewLat);
        TextView longitudeField = findViewById(R.id.textViewLong);
        TextView phoneNumberField = findViewById(R.id.textViewPhone);
        TextView restrictionsField = findViewById(R.id.textViewRestrictions);
        TextView ageRangeField = findViewById(R.id.textViewAgeRange);
        TextView genderField = findViewById(R.id.textViewGender);
        TextView specialNotesField = findViewById(R.id.textViewSpecialNotes);
        TextView currentAvailabilityCount = findViewById(R.id
                .textViewCurrentAvailability);

        Spinner reserveSpinner = findViewById(R.id.reserveSpinner);
        reserveButton = findViewById(R.id.reserveButton);


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
        ageRangeField.setText(shelterAgeRange);
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
        if ("-".equals(temp)) {
            numberOfSpaces = 0.0;
        } else {
            numberOfSpaces = Double.valueOf(temp);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
