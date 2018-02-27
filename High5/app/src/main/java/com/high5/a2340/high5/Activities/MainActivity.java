package com.high5.a2340.high5.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

    private void logout() {
        progressDialog.setMessage("Logging out...");
        progressDialog.show();
        fireBaseAuth.signOut();
        progressDialog.dismiss();
    }

    private void populateShelters() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        shelterKeys = new ArrayList<>();
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
        shelterKeys.add("Special Notes");
        defaultValues.add("No Value");

        mDatabase.child("shelter-data").addListenerForSingleValueEvent(new ValueEventListener() {
            List<Shelter> shelterList = new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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

                    adapter.add(new Shelter((String) shelterSpecs.get(0),
                            String.valueOf(shelterSpecs.get(1)),
                            (Double) shelterSpecs.get(2),
                            (Double) shelterSpecs.get(3),
                            (String) shelterSpecs.get(4),
                            (String) shelterSpecs.get(5),
                            (String) shelterSpecs.get(6),
                            (String) shelterSpecs.get(7)).toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
