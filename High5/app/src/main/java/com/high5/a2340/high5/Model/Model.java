package com.high5.a2340.high5.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.StringBufferInputStream;
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
    private DatabaseReference mDatabase;

    public void populateShelters() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final List shelterSpecs = new ArrayList();
        shelterSpecs.add("Address");
        shelterSpecs.add("Capacity");
        shelterSpecs.add("Latitude");
        shelterSpecs.add("Longitude");
        shelterSpecs.add("Phone Number");
        shelterSpecs.add("Restrictions");
        shelterSpecs.add("Shelter Name");
        shelterSpecs.add("Special Notes");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot shelters = dataSnapshot.child("shelter-data");
                for (DataSnapshot shelter : shelters.getChildren()) {
                    int index = 0;
                    for (Object value : shelterSpecs) {
                        if (shelter.child((String) value).getValue() != null
                                && !(shelter.child((String) value).equals(""))) {
                            shelterSpecs.remove(index);
                            shelterSpecs.add(index, shelter.child((String) value).getValue());
                        } else {
                            shelterSpecs.remove(index);
                            shelterSpecs.add(index, "No Value");
                        }
                        index++;
                    }
                    shelterList.add(new Shelter((String) shelterSpecs.get(0)
                            , (Integer) shelterSpecs.get(1)
                            , (Double) shelterSpecs.get(2)
                            , (Double) shelterSpecs.get(3)
                            , (String) shelterSpecs.get(4)
                            , (String) shelterSpecs.get(5)
                            , (String) shelterSpecs.get(6)
                            , (String) shelterSpecs.get(7)));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
