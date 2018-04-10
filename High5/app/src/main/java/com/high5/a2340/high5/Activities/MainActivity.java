package com.high5.a2340.high5.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.high5.a2340.high5.Model.Shelter;
import com.high5.a2340.high5.Model.UserTypes;
import com.high5.a2340.high5.Model.AgeRange;
import com.high5.a2340.high5.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  The activity for loading and listing shelters
 *  @author High5
 *  @version 1.4
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth fireBaseAuth;

    //private Button logoutButton;
    //private Button searchButton;

    private ProgressDialog progressDialog;
    private ArrayAdapter adapter;

    public static final List<String> legalUserTypes = Arrays.asList(UserTypes.USER.getValue(),
            UserTypes.ADMIN.getValue(),
            UserTypes.EMPLOYEE.getValue());
    private List<String> shelterKeys;
    //private List defaultValues;
    private List<Shelter> shelterList;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = findViewById(R.id.listView);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        fireBaseAuth = FirebaseAuth.getInstance();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);



        listView.setOnItemClickListener(this);


        adapter = new ArrayAdapter(this, R.layout.simple_list_item);
        listView.setAdapter(adapter);
        populateShelters();

    }

    @Override
    protected void onResume() {
        updateShelterList();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu );
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorPrimary, null)
                        , PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                logout();
                finish();
                break;
            case R.id.search:
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                myIntent.putExtra("shelterList", (Serializable) shelterList);
                startActivity(myIntent);
                break;
            case R.id.cancelReservation:
                cancleReservation();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private void cancleReservation() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userID = fireBaseAuth.getCurrentUser().getUid();
                if (dataSnapshot.child("reservations").hasChild(userID)) {
                    String shelterIDToChange = (String) dataSnapshot.child("reservations")
                            .child(userID).child("ShelterID").getValue();
                    String shelterName = dataSnapshot.child("reservations").child(userID)
                            .child("location").getValue().toString();
                    Double spotsReserved = Double.valueOf(dataSnapshot
                            .child("reservations").child(userID)
                            .child("numberOfSpots").getValue().toString());

                    Double oldAvailability = Double.valueOf(dataSnapshot.child("shelter-data")
                            .child(shelterIDToChange).child("currentAvailability")
                            .getValue().toString());
                    Double newAvailability = oldAvailability + spotsReserved;
                            mDatabase.child("shelter-data").child(shelterIDToChange)
                            .child("currentAvailability")
                            .setValue((newAvailability.intValue()));
                    //remove user reservation
                    mDatabase.child("reservations").child(userID).removeValue();

                    // update local shelter list
                    for (Shelter s : shelterList) {
                        if (s.getShelterID().equals(shelterIDToChange)) {
                            s.setCurrentAvailability(newAvailability.intValue());
                        }
                    }


                    Toast.makeText(MainActivity.this,
                            "Reservation for " + spotsReserved.intValue() + " at "
                            + shelterName + " has been canceled.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "No Reservations Made",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
    private void updateShelterList() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String shelterIDToChange = (String) dataSnapshot.child("reservations")
                        .child(fireBaseAuth
                        .getCurrentUser().getUid()).child("ShelterID").getValue();
                for (Shelter s : shelterList) {
                    if (s.getShelterID().equals(shelterIDToChange)) {
                        Double availability = Double.valueOf(dataSnapshot.child("shelter-data")
                                .child(shelterIDToChange)
                                .child("currentAvailability").getValue().toString());
                        s.setCurrentAvailability(availability.intValue());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void populateShelters() {
        shelterKeys = new ArrayList<>();
        shelterList = new ArrayList<>();
        //defaultValues = new ArrayList();
        shelterKeys.add("address");
        shelterKeys.add("capacity");
        shelterKeys.add("latitude");
        shelterKeys.add("longitude");
        shelterKeys.add("phoneNumber");
        shelterKeys.add("restrictions");
        shelterKeys.add("shelterName");
        shelterKeys.add("female");
        shelterKeys.add("male");
        shelterKeys.add("specialNotes");
        shelterKeys.add("ageRange");
        shelterKeys.add("currentAvailability");


        mDatabase.child("shelter-data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String shelterID;
                progressDialog.setMessage("Retrieving Data");
                progressDialog.show();
                for (DataSnapshot shelter : dataSnapshot.getChildren()) {
                    shelterID = shelter.getKey();
                    List shelterSpecs = new ArrayList();
                    for (String value : shelterKeys) {
                        shelterSpecs.add(shelter.child(value).getValue());
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
                            AgeRange.valueOf((String) shelterSpecs.get(10)),
                            shelterID,
                            Integer.parseInt(String.valueOf(shelterSpecs.get(11))));
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

    @Override
    public void onBackPressed() {
    }
}
