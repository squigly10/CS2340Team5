package com.high5.a2340.high5.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.high5.a2340.high5.R;
import com.high5.a2340.high5.Model.Shelter;

import java.util.List;
import java.util.StringJoiner;

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

    private static final float INITIAL_ZOOM = 12.0f;
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
            LatLng pos = new LatLng(e.getLatitude(), e.getLongitude());
            mMap.addMarker(new MarkerOptions().position(pos).title(e.getShelterName())
                    .snippet(formatPhoneNum(e.getPhoneNumber())));
        }
        //move camera to focus on first shelter in the list
        if (shelterList.size() != 0) {
            Shelter example = shelterList.get(0);
            LatLng pos = new LatLng(example.getLatitude(), example.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(INITIAL_ZOOM));
        } else {
            LatLng pos = new LatLng(33.749, -84.388);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        }
    }

    public String formatPhoneNum(String input) {
        StringJoiner finalPhone = new StringJoiner("-");
        input = cleanupString(input);
        int length = input.length();
        int max = length - 2 * Math.floorMod(3 - length, 3);
        for (int i = 0; i < max; i += 3) {
            finalPhone.add(input.substring(i, i + 3));
        }
        for (int i = max; i < length; i += 4) {
            finalPhone.add(input.substring(i, i + 4));
        }
        return finalPhone.toString();
    }

    private String cleanupString(String input) {
        return input.replaceAll("[^\\d]", "");
    }
}
