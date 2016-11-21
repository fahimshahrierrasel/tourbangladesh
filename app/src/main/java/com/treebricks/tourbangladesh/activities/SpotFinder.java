package com.treebricks.tourbangladesh.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.adapter.SuggestionCardAdapter;
import com.treebricks.tourbangladesh.database.DatabaseHelper;
import com.treebricks.tourbangladesh.model.SpotModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpotFinder extends AppCompatActivity {

    List<String> catagories;
    RecyclerView finderRecyclerView;
    List<SpotModel> allSpots;
    SuggestionCardAdapter suggestionCardAdapter;
    SharedPreferences getPrefs;
    Cursor cursor = null;
    Location myLocation;
    DatabaseHelper databaseHelper;
    View spotFinderView;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_finder);

        spotFinderView = findViewById(R.id.finder_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_spot_finder);
        setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        finderRecyclerView = (RecyclerView) findViewById(R.id.finder_recyclerview);
        finderRecyclerView.setLayoutManager(new LinearLayoutManager(SpotFinder.this, LinearLayoutManager.VERTICAL, false));

        getPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        catagories = new ArrayList<>();
        allSpots = new ArrayList<SpotModel>();

        String myLatitude = getPrefs.getString("latitude", "23.777785");
        String myLongitude = getPrefs.getString("longitude", "90.423011");

        myLocation = new Location(LocationManager.GPS_PROVIDER);
        myLocation.setLatitude(Double.parseDouble(myLatitude));
        myLocation.setLongitude(Double.parseDouble(myLongitude));

        databaseHelper = new DatabaseHelper(SpotFinder.this);
        try{
            databaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = databaseHelper.rawQuery("Select Distinct Catagory From FamousSpot", null);
        if (cursor.moveToFirst()) {
            do {
                String catagory = cursor.getString(cursor.getColumnIndex("Catagory"));
                catagories.add(catagory);
            } while (cursor.moveToNext());
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                databaseHelper.close();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catagories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                allSpots.clear();
                progress = ProgressDialog.show(SpotFinder.this, "Loading...",
                        "Have patience.\nEverything is difficult before it is easy.", true);

                try{
                    databaseHelper.openDataBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                cursor = databaseHelper.rawQuery("Select * From FamousSpot Natural Join SpotImage Where Catagory = ?",
                        new String[]{catagories.get(i)});
                if (cursor.moveToFirst()) {
                    do {
                        String spotId = cursor.getString(cursor.getColumnIndex("SpotID"));
                        String spotName = cursor.getString(cursor.getColumnIndex("SpotName"));
                        String spotCatagory = cursor.getString(cursor.getColumnIndex("Catagory"));
                        String spotDistrict = cursor.getString(cursor.getColumnIndex("District"));
                        String spotLocation = cursor.getString(cursor.getColumnIndex("SpotLocation"));
                        String spotDescription = cursor.getString(cursor.getColumnIndex("SpotDescription"));
                        String spotLatitude = cursor.getString(cursor.getColumnIndex("Latitude"));
                        String spotLongitude = cursor.getString(cursor.getColumnIndex("Longitude"));
                        String spotImage = cursor.getString(cursor.getColumnIndex("ImageURL"));

                        allSpots.add(new SpotModel(spotId,spotName, spotCatagory,
                                spotLocation, spotDistrict, spotDescription, spotLatitude, spotLongitude, spotImage));
                    } while (cursor.moveToNext());
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                        databaseHelper.close();
                    }
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        suggestionCardAdapter = new SuggestionCardAdapter(allSpots, myLocation, SpotFinder.this);
                        finderRecyclerView.setAdapter(suggestionCardAdapter);
                    }
                }, 1500);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Snackbar.make(spotFinderView, "There is nothing to show.\nPlease select something else.",
                        Snackbar.LENGTH_SHORT).show();

            }
        });

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
