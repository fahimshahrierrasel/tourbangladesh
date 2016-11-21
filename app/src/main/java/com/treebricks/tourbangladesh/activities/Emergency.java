package com.treebricks.tourbangladesh.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.adapter.EmergencyCardAdapter;
import com.treebricks.tourbangladesh.adapter.SuggestionCardAdapter;
import com.treebricks.tourbangladesh.model.EmergencyModel;

import java.util.ArrayList;
import java.util.List;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class Emergency extends AppCompatActivity implements PlacesListener {

    View emergencyView;
    RecyclerView emergencyRecyclerView;
    SharedPreferences getPrefs;

    String parameter;

    String latitudeString;
    String longitudeString;

    List<String> catagories;

    ProgressDialog progress;

    List<EmergencyModel> allEmergency;

    EmergencyCardAdapter emergencyCardAdapter;

    Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_emergency);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Emergency services around you");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        catagories = new ArrayList<String>();
        allEmergency = new ArrayList<EmergencyModel>();

        catagories.add("ATM");
        catagories.add("Bank");
        catagories.add("Cafe");
        catagories.add("Embassy");
        catagories.add("Fire Station");
        catagories.add("Hospital");
        catagories.add("Hotels & Resturents");
        catagories.add("Pharmacy");

        emergencyView = findViewById(R.id.emergency_container);

        Spinner spinner = (Spinner) findViewById(R.id.emergency_spinner);
        emergencyRecyclerView = (RecyclerView) findViewById(R.id.emergency_recyclerview);
        emergencyRecyclerView.setLayoutManager(new LinearLayoutManager(Emergency.this, LinearLayoutManager.VERTICAL, false));

        latitudeString = getPrefs.getString("latitude", "00.0000000");
        longitudeString = getPrefs.getString("longitude", "00.0000000");
        parameter = getPrefs.getString("parameter", "10");

        myLocation = new Location(LocationManager.GPS_PROVIDER);
        myLocation.setLatitude(Double.parseDouble(latitudeString));
        myLocation.setLongitude(Double.parseDouble(longitudeString));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catagories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                switch (i)
                {
                    case 0:
                    {

                        new NRPlaces.Builder()
                                .listener(Emergency.this)
                                .latlng(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString))
                                .radius(Integer.parseInt(parameter) * 1000)
                                .type(PlaceType.ATM)
                                .key("AIzaSyD8DqyAIjH8TVcPLQpmmyXUEZ4QtETq7Fo")
                                .build()
                                .execute();

                        break;
                    }
                    case 1:
                    {


                        new NRPlaces.Builder()
                                .listener(Emergency.this)
                                .latlng(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString))
                                .radius(Integer.parseInt(parameter) * 1000)
                                .type(PlaceType.BANK)
                                .key("AIzaSyD8DqyAIjH8TVcPLQpmmyXUEZ4QtETq7Fo")
                                .build()
                                .execute();

                        break;
                    }
                    case 2:
                    {


                        new NRPlaces.Builder()
                                .listener(Emergency.this)
                                .latlng(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString))
                                .radius(Integer.parseInt(parameter) * 1000)
                                .type(PlaceType.CAFE)
                                .key("AIzaSyD8DqyAIjH8TVcPLQpmmyXUEZ4QtETq7Fo")
                                .build()
                                .execute();
                        break;
                    }
                    case 3:
                    {


                        new NRPlaces.Builder()
                                .listener(Emergency.this)
                                .latlng(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString))
                                .radius(Integer.parseInt(parameter) * 1000)
                                .type(PlaceType.EMBASSY)
                                .key("AIzaSyD8DqyAIjH8TVcPLQpmmyXUEZ4QtETq7Fo")
                                .build()
                                .execute();
                        break;
                    }
                    case 4:
                    {



                        new NRPlaces.Builder()
                                .listener(Emergency.this)
                                .latlng(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString))
                                .radius(Integer.parseInt(parameter) * 1000)
                                .type(PlaceType.FIRE_STATION)
                                .key("AIzaSyD8DqyAIjH8TVcPLQpmmyXUEZ4QtETq7Fo")
                                .build()
                                .execute();
                        break;
                    }
                    case 5:
                    {


                        new NRPlaces.Builder()
                                .listener(Emergency.this)
                                .latlng(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString))
                                .radius(Integer.parseInt(parameter) * 1000)
                                .type(PlaceType.HOSPITAL)
                                .key("AIzaSyD8DqyAIjH8TVcPLQpmmyXUEZ4QtETq7Fo")
                                .build()
                                .execute();
                        break;
                    }
                    case 6:
                    {


                        new NRPlaces.Builder()
                                .listener(Emergency.this)
                                .latlng(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString))
                                .radius(Integer.parseInt(parameter) * 1000)
                                .type(PlaceType.RESTAURANT)
                                .key("AIzaSyD8DqyAIjH8TVcPLQpmmyXUEZ4QtETq7Fo")
                                .build()
                                .execute();
                        break;
                    }
                    case 7:
                    {



                        new NRPlaces.Builder()
                                .listener(Emergency.this)
                                .latlng(Double.parseDouble(latitudeString), Double.parseDouble(longitudeString))
                                .radius(Integer.parseInt(parameter) * 1000)
                                .type(PlaceType.PHARMACY)
                                .key("AIzaSyD8DqyAIjH8TVcPLQpmmyXUEZ4QtETq7Fo")
                                .build()
                                .execute();
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

        progress = ProgressDialog.show(Emergency.this, "Loading...",
                "Have patience.\nEverything is difficult before it is easy.", true);

    }

    @Override
    public void onPlacesSuccess(List<Place> places) {

        allEmergency.clear();

        for(Place place : places)
        {
            allEmergency.add(new EmergencyModel(place.getName(), place.getVicinity(), place.getLatitude(), place.getLongitude()));
        }

    }

    @Override
    public void onPlacesFinished() {

        progress.dismiss();
        emergencyCardAdapter = new EmergencyCardAdapter(Emergency.this, allEmergency, myLocation);
        emergencyRecyclerView.setAdapter(emergencyCardAdapter);
    }
}
