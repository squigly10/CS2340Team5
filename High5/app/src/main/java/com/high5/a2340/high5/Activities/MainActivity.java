package com.high5.a2340.high5.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.high5.a2340.high5.Model.AgeRange;
import com.high5.a2340.high5.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth fireBaseAuth;

    private Button logoutButton;
    private Button searchButton;

    private ListView listView;

    private ProgressDialog progressDialog;
    private ArrayAdapter adapter;
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

        //logoutButton = (Button) findViewById(R.id.logoutButton);
        //searchButton = (Button) findViewById(R.id.searchButton);

        listView = (ListView) findViewById(R.id.listView);

        progressDialog = new ProgressDialog(this);

        fireBaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);


        //logoutButton.setOnClickListener(this);
        //searchButton.setOnClickListener(this);

        listView.setOnItemClickListener(this);

        adapter = new ArrayAdapter(this, R.layout.simple_list_item);
        listView.setAdapter(adapter);
        populateShelters();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu );
        return super.onCreateOptionsMenu(menu);
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
            default:

        }
        return super.onOptionsItemSelected(item);
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

    private void populateShelters() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        shelterKeys = new ArrayList<>();
        shelterList = new ArrayList<>();
        defaultValues = new ArrayList();
        shelterKeys.add("address");
        defaultValues.add("No Value");
        shelterKeys.add("capacity");
        defaultValues.add(0L);
        shelterKeys.add("latitude");
        defaultValues.add(0.0);
        shelterKeys.add("longitude");
        defaultValues.add(0.0);
        shelterKeys.add("phoneNumber");
        defaultValues.add("No Value");
        shelterKeys.add("restrictions");
        defaultValues.add("No Value");
        shelterKeys.add("shelterName");
        defaultValues.add("No Value");
        shelterKeys.add("female");
        defaultValues.add(true);
        shelterKeys.add("male");
        defaultValues.add(true);
        shelterKeys.add("specialNotes");
        defaultValues.add("No Value");
        shelterKeys.add("ageRange");
        defaultValues.add("ANYONE");

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
                            (String) shelterSpecs.get(9),
                            AgeRange.valueOf((String) shelterSpecs.get(10)));
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
