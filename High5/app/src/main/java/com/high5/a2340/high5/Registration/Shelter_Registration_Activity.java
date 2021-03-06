package com.high5.a2340.high5.Registration;

import android.app.ProgressDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.high5.a2340.high5.Model.AgeRange;
import com.high5.a2340.high5.R;

/**
 *  Activity for registering a new Shelter into the database
 *  @author High5
 *  @version 1.4
 */
public class Shelter_Registration_Activity extends AppCompatActivity
        implements View.OnClickListener {

    //private static final String TAG = "EmailPassword";

    private FirebaseAuth firebaseAuth;

    private EditText name;
    private EditText address;
    private EditText phoneNumber;
    private EditText capacity;
    private EditText notes;
    private EditText restrictions;

    private Spinner ageRange;

    private CheckBox male;
    private CheckBox female;

    private Button addShelter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_registration_);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInAnonymously();

        name = findViewById(R.id.nameField);
        address = findViewById(R.id.addressField);
        phoneNumber = findViewById(R.id.numberField);
        ageRange = findViewById(R.id.restrictionsSpinner);
        capacity = findViewById(R.id.capacityField);
        notes = findViewById(R.id.notesField);
        restrictions = findViewById(R.id.restrictionsField);

        male = findViewById(R.id.maleCheckBox);
        female = findViewById(R.id.femaleCheckbox);

        addShelter = findViewById(R.id.addShelterButton);
        SpinnerAdapter restrictionsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, AgeRange.values());
        ageRange.setAdapter(restrictionsAdapter);
        ageRange.setSelection(0);
        progressDialog = new ProgressDialog(this);

        addShelter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addShelter) {
            if (validateEntries()) {
                addShelter();
            }
        }

    }
    private boolean validateEntries() {
        boolean status = true;
        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError("Required");
            status = false;
        }
        if (TextUtils.isEmpty(address.getText().toString())) {
            address.setError("Required");
            status = false;
        }
        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            phoneNumber.setError("Required");
            status = false;
        } else if (phoneNumber.getText().toString().length() != 10) {
            phoneNumber.setError("Please enter a 10 digit number");
            status = false;
        }
        if (TextUtils.isEmpty(capacity.getText().toString())) {
            capacity.setError("Required");
            status = false;
        }
        if (TextUtils.isEmpty(notes.getText().toString())) {
            notes.setText("No Special Notes");
        }
        if (TextUtils.isEmpty(restrictions.getText().toString())) {
            restrictions.setText("No Restrictions");
        }
        return status;
    }
    @Override
    public void onBackPressed() {
        firebaseAuth.getCurrentUser().delete();
        finish();
    }
    private void addShelter() {
        progressDialog.setMessage("Shelter is being Added");
        progressDialog.show();
        try {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            String uID = firebaseAuth.getUid();
            DatabaseReference shelter = mDatabase.child("shelter-data").child(uID);
            shelter.child("shelterName").setValue(name.getText().toString());
            shelter.child("address").setValue(address.getText().toString());
            shelter.child("capacity").setValue(Integer.parseInt(capacity.getText().toString()));
            shelter.child("currentAvailability").setValue(Integer.parseInt(capacity
                    .getText().toString()));
            shelter.child("female").setValue(female.isChecked());
            shelter.child("longitude").setValue(34.01);
            shelter.child("latitude").setValue(-23.07);
            shelter.child("male").setValue(male.isChecked());
            shelter.child("phoneNumber").setValue((phoneNumber.getText().toString()));
            shelter.child("ageRange").setValue(ageRange.getSelectedItem());
            shelter.child("restrictions").setValue(restrictions.getText().toString());
            shelter.child("specialNotes").setValue(notes.getText().toString());
            Toast.makeText(Shelter_Registration_Activity.this, "Shelter Added",
                    Toast.LENGTH_SHORT).show();
            firebaseAuth.getCurrentUser().delete();
            progressDialog.dismiss();
            finish();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(this, "Shelter was unable to be added",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
