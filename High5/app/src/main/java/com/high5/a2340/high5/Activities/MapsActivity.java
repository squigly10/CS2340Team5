package com.high5.a2340.high5.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.high5.a2340.high5.Model.Shelter;

import java.util.List;package com.high5.a2340.high5.Activities;

/**
 * Created by hsmul on 3/27/2018.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.high5.a2340.high5.Model.Shelter;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Shelter> shelterList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        shelterList = (List<Shelter>) getIntent().getSerializableExtra("shelterList");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a markers
        for (Shelter e : shelterList) {
            LatLng pos = new LatLng(e.getLatitude(), e.getLatitude());
            mMap.addMarker(new MarkerOptions().position(pos).title(e.getShelterName()));
        }
        //move camera to focus on first shelter in the list
        Shelter example = shelterList.get(0);
        LatLng pos = new LatLng(example.getLatitude(), example.getLatitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng());
    }
}
