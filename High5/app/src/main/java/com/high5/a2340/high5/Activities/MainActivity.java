package com.high5.a2340.high5.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.high5.a2340.high5.Model.Shelter;
import com.high5.a2340.high5.Model.UserTypes;
import com.high5.a2340.high5.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private FirebaseAuth fireBaseAuth;

    private Button logoutButton;

    private ListView listView;

    private ProgressDialog progressDialog;
    private ArrayAdapter adapter;

    public static final List<String> legalUserTypes = Arrays.asList(UserTypes.USER.getValue(),
            UserTypes.ADMIN.getValue(),
            UserTypes.EMPLOYEE.getValue());

    public List<String> shelterKeys;
    public List defaultValues;
    public List<Shelter> shelterList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = (Button) findViewById(R.id.logoutButton);

        listView = (ListView) findViewById(R.id.listView);

        progressDialog = new ProgressDialog(this);

        fireBaseAuth = FirebaseAuth.getInstance();

        logoutButton.setOnClickListener(this);

        listView.setOnItemClickListener(this);

        adapter = new ArrayAdapter(this, R.layout.simple_list_item);
        listView.setAdapter(adapter);
        populateShelters();

    }

    @Override
    public void onClick(View view) {
        if (view == logoutButton) {
            logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Shelter selected = shelterList.get(position);
        Intent myIntent = new Intent(MainActivity.this, ShelterInfoActivity.class);
        myIntent.putExtra("address", selected.getAddress());
        myIntent.putExtra("capacity", selected.getCapacity());
        myIntent.putExtra("latitude", selected.getLatitude());
        myIntent.putExtra("longitude", selected.getLongitude());
        myIntent.putExtra("phoneNumber", selected.getPhoneNumber());
        myIntent.putExtra("restrictions", selected.getRestrictions());
        myIntent.putExtra("shelterName", selected.getShelterName());
        myIntent.putExtra("isFemale", selected.isFemale());
        myIntent.putExtra("isMale", selected.isMale());
        myIntent.putExtra("specialNotes", selected.getSpecialNotes());
        startActivity(myIntent);
    }

    private void logout() {
        progressDialog.setMessage("Logging out...");
        progressDialog.show();
        fireBaseAuth.signOut();
        progressDialog.dismiss();
    }

    private void populateShelters() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        shelterKeys = new ArrayList<>();
        shelterList = new ArrayList<>();
        defaultValues = new ArrayList();
        shelterKeys.add("Address");
        defaultValues.add("No Value");
        shelterKeys.add("Capacity");
        defaultValues.add("No Value");
        shelterKeys.add("Latitude");
        defaultValues.add(0.0);
        shelterKeys.add("Longitude");
        defaultValues.add(0.0);
        shelterKeys.add("Phone Number");
        defaultValues.add("No Value");
        shelterKeys.add("Restrictions");
        defaultValues.add("No Value");
        shelterKeys.add("Shelter Name");
        defaultValues.add("No Value");
        shelterKeys.add("Females");
        defaultValues.add(true);
        shelterKeys.add("Males");
        defaultValues.add(true);
        shelterKeys.add("Special Notes");
        defaultValues.add("No Value");

        mDatabase.child("shelter-data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.setMessage("Retrieving Data");
                progressDialog.show();
                for (DataSnapshot shelter : dataSnapshot.getChildren()) {
                    List shelterSpecs = new ArrayList();
                    int index = 0;
                    for (String value : shelterKeys) {
                        if (shelter.child(value).getValue() != null
                                && !(shelter.child(value).equals(""))) {
                            shelterSpecs.add(shelter.child(value).getValue());
                        } else {
                            shelterSpecs.add(defaultValues.get(index));
                        }
                        index++;
                    }
                    Shelter temp = new Shelter((String) shelterSpecs.get(0),
                            String.valueOf(shelterSpecs.get(1)),
                            (Double) shelterSpecs.get(2),
                            (Double) shelterSpecs.get(3),
                            (String) shelterSpecs.get(4),
                            (String) shelterSpecs.get(5),
                            (String) shelterSpecs.get(6),
                            (boolean) shelterSpecs.get(7),
                            (boolean) shelterSpecs.get(8),
                            (String) shelterSpecs.get(9));
                    adapter.add(temp.toString());
                    shelterList.add(temp);

                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
