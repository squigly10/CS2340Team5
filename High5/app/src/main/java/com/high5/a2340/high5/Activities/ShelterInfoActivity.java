package com.high5.a2340.high5.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.high5.a2340.high5.R;

/**
 * Created by henri on 2/27/18.
 */

public class ShelterInfoActivity extends AppCompatActivity implements View.OnClickListener {


    private Button backButton;
    private TextView nameField;
    private TextView capacityField;
    private TextView addressField;
    private TextView latitudeField;
    private TextView longitudeField;
    private TextView phoneNumberField;
    private TextView genderField;

    private String address;
    private String capacity;
    private Double latitude;
    private Double longitude;
    private String phoneNumber;
    private String restrictions;
    private String shelterName;
    private String specialNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelter_info);

        address = getIntent().getExtras().getString("address");
        capacity = getIntent().getExtras().getString("capacity");
        shelterName = getIntent().getExtras().getString("shelterName");
        latitude = getIntent().getExtras().getDouble("latitude");
        longitude = getIntent().getExtras().getDouble("longitude");
        phoneNumber = getIntent().getExtras().getString("phoneNumber");
        restrictions = getIntent().getExtras().getString("restrictions");
        specialNotes = getIntent().getExtras().getString("specialNotes");

        backButton = (Button) findViewById(R.id.buttonBack);
        nameField = (TextView) findViewById(R.id.textViewName);
        capacityField = (TextView) findViewById(R.id.textViewCapacity);
        addressField = (TextView) findViewById(R.id.textViewAddress);
        latitudeField = (TextView) findViewById(R.id.textViewLat);
        longitudeField = (TextView) findViewById(R.id.textViewLong);
        phoneNumberField = (TextView) findViewById(R.id.textViewPhone);
        genderField = (TextView) findViewById(R.id.textViewGender);


        nameField.setText(shelterName);
        capacityField.setText(capacity);
        addressField.setText(address);
        latitudeField.setText(latitude.toString());
        longitudeField.setText(longitude.toString());
        phoneNumberField.setText(phoneNumber);
        genderField.setText(restrictions);

        backButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == backButton) {
            startActivity(new Intent(ShelterInfoActivity.this, MainActivity.class));
        }
    }
}
