package com.high5.a2340.high5.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.high5.a2340.high5.Model.Shelter;
import com.high5.a2340.high5.Model.AgeRange;
import com.high5.a2340.high5.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hsmulders on 3/6/18.
 */


public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private EditText searchText;
    private Spinner ageSpinner;
    private CheckBox maleBox;
    private CheckBox femaleBox;
    private List<Shelter> shelterList;
    private List<Shelter> filteredList;
    private ArrayAdapter listAdapter;
    private ArrayAdapter<AgeRange> ageAdapter;
    private ListView listView;
    private TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        maleBox = (CheckBox) findViewById(R.id.maleBox);
        femaleBox = (CheckBox) findViewById(R.id.femaleBox);
        listView = (ListView) findViewById(R.id.listView);
        ageSpinner = (Spinner) findViewById(R.id.spinner);
        searchText = (EditText) findViewById(R.id.searchText);
        emptyText = (TextView) findViewById(R.id.emptyTextView);

        listView.setOnItemClickListener(this);

        shelterList = (List<Shelter>) getIntent().getSerializableExtra("shelterList");
        filteredList = new ArrayList<Shelter>();

        listAdapter = new ArrayAdapter(this, R.layout.simple_list_item);

        ageAdapter = new ArrayAdapter<AgeRange>(this, android.R.layout.simple_spinner_item, AgeRange.values());
        ageSpinner.setAdapter(ageAdapter);
        ageSpinner.setSelection(ageAdapter.getPosition(AgeRange.ANYONE));
        listView.setAdapter(listAdapter);
        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter();
            }
        });

        maleBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filter();
            }
        });
        femaleBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filter();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Shelter selected = filteredList.get(position);
        Intent myIntent = new Intent(SearchActivity.this, ShelterInfoActivity.class);
        myIntent.putExtra("shelter", selected);
        startActivity(myIntent);
    }

    //TODO: IMPLEMENT FUZZY SEARCH
    private void filter(){
        listAdapter.clear();
        filteredList.clear();
        boolean male = maleBox.isChecked();
        boolean female = femaleBox.isChecked();
        AgeRange age = (AgeRange) ageSpinner.getSelectedItem();
        String text = searchText.getText().toString();
        for (int i = 0; i < shelterList.size(); i++) {
            if(shelterList.get(i).getAgeRange().equals(age) || age.equals(AgeRange.ANYONE)) {
                if (male && female) {
                    filteredList.add(shelterList.get(i));
                } else if (shelterList.get(i).isMale() == male && shelterList.get(i).isFemale() == female) {
                    filteredList.add(shelterList.get(i));
                }
            }
        }
        if (!text.equals("")){
            Iterator<Shelter> iter = filteredList.iterator();

            while (iter.hasNext()) {
                Shelter temp = iter.next();

                if (!temp.getShelterName().toLowerCase().contains(text.toLowerCase())){
                    iter.remove();
                }
            }
        }
        for (Shelter temp : filteredList){
            listAdapter.add(temp);
        }
        listView.setAdapter(listAdapter);
        listView.setEmptyView(emptyText);
    }

}

