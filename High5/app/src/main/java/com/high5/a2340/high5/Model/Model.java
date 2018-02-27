package com.high5.a2340.high5.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by david on 2/19/2018.
 */

public class Model {

    public static final List<String> legalUserTypes = Arrays.asList(UserTypes.USER.getValue(),
                                                                            UserTypes.ADMIN.getValue(),
                                                                            UserTypes.EMPLOYEE.getValue());
    public List<Shelter> shelterList;
    public List<String> shelterKeys;
    public List defaultValues;
    private DatabaseReference mDatabase;

    public void populateShelters() {
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
        shelterKeys.add("Special Notes");
        defaultValues.add("No Value");

        mDatabase.child("shelter-data").addListenerForSingleValueEvent(new ValueEventListener() {
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

                    shelterList.add(new Shelter((String) shelterSpecs.get(0),
                            String.valueOf(shelterSpecs.get(1)),
                            (Double) shelterSpecs.get(2),
                            (Double) shelterSpecs.get(3),
                            (String) shelterSpecs.get(4),
                            (String) shelterSpecs.get(5),
                            (String) shelterSpecs.get(6),
                            (String) shelterSpecs.get(7)));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
