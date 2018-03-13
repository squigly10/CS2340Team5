package com.high5.a2340.high5.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.high5.a2340.high5.Model.Shelter;
import com.high5.a2340.high5.Model.User;
import com.high5.a2340.high5.Model.UserTypes;
import com.high5.a2340.high5.Model.AgeRange;
import com.high5.a2340.high5.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth fireBaseAuth;
    //variables for cancelReservation()
    private DatabaseReference userDbReference;
    private DatabaseReference shelterDbReference;
    Long shelterKey;
    Long numberOfBeds;
    Shelter currentShelter;
    private String userID;
    private User currentUser;

    private ListView listView;
    ArrayAdapter<String> adapter;

    private ProgressDialog progressDialog;
    private android.support.v7.widget.Toolbar toolbar;

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

        listView = (ListView) findViewById(R.id.listView);

        progressDialog = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        fireBaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        listView.setOnItemClickListener(this);

        adapter = new ArrayAdapter(this, R.layout.simple_list_item);
        listView.setAdapter(adapter);
        populateShelters();
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
        shelterDbReference = FirebaseDatabase.getInstance().getReference("shelter-data/");
    }


    protected void onResume() {
        super.onResume();

        fireBaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        progressDialog = new ProgressDialog(this);

        fireBaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        listView.setOnItemClickListener(this);

        adapter = new ArrayAdapter(this, R.layout.simple_list_item);
        listView.setAdapter(adapter);
        populateShelters();
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
        shelterDbReference = FirebaseDatabase.getInstance().getReference("shelter-data/");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                logout();
                finish();
                return true;
            case R.id.search:
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                myIntent.putExtra("shelterList", (Serializable) shelterList);
                startActivity(myIntent);
                return true;
            case R.id.cancelReservation:
                cancelReservation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void cancelReservation() {
        if (!currentUser.isHasReservation()) {
            Toast.makeText(MainActivity.this, "You have no active reservations",
                    Toast.LENGTH_LONG).show();
        } else {
            userDbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    shelterKey = Long.parseLong(String.valueOf(dataSnapshot.child("reservation-info").child("shelter-key").getValue()));
                    numberOfBeds = Long.parseLong(String.valueOf(dataSnapshot.child("reservation-info").child("beds-reserved").getValue()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });
            shelterDbReference.child(shelterKey + "").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentShelter = dataSnapshot.getValue(Shelter.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });
            Toast.makeText(MainActivity.this, "Shelter key is" + shelterKey,
                    Toast.LENGTH_LONG).show();
            /*
            currentShelter.setCurrentAvailability(currentShelter.getCurrentAvailability() + numberOfBeds);
            shelterDbReference.child(shelterKey + "").setValue(currentShelter);
            currentUser.setHasReservation(false);
            userDbReference.setValue(currentUser);
            */
            //userDbReference.child("reservation-info").setValue(null);
        }
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Shelter selected = shelterList.get(position);
        Intent myIntent = new Intent(MainActivity.this, ShelterInfoActivity.class);
        myIntent.putExtra("shelter", selected);
        startActivity(myIntent);
    }

    private void logout() {
        progressDialog.setMessage("Logging out...");
        progressDialog.show();
        fireBaseAuth.signOut();
        progressDialog.dismiss();
    }

    //TODO: I DONT THINK GENDER AND AGE RANGE FIELDS ARE POPULATED CORRECTLY
    private void populateShelters() {
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
        shelterKeys.add("Age Range");
        defaultValues.add(AgeRange.ANYONE);
        shelterKeys.add("Unique Key");
        defaultValues.add(new Long(0));

        mDatabase.child("shelter-data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.setMessage("Retrieving Data");
                progressDialog.show();
                adapter.clear();
                shelterList.clear();
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
                            (String) shelterSpecs.get(9),
                            (AgeRange) shelterSpecs.get(10),
                            (Long) shelterSpecs.get(11));
                    adapter.add(temp.toString());

                    shelterList.add(temp);
                    adapter.notifyDataSetChanged();

                }
                progressDialog.dismiss();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(MainActivity.this, "You have no active reservations",
                Toast.LENGTH_LONG).show();
    }
}
