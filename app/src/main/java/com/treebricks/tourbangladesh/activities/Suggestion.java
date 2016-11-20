package com.treebricks.tourbangladesh.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.adapter.SuggestionCardAdapter;
import com.treebricks.tourbangladesh.database.DatabaseHelper;
import com.treebricks.tourbangladesh.model.SpotModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Suggestion extends AppCompatActivity {

    RecyclerView spotsRecyclerView;
    List<SpotModel> allSpots;
    SuggestionCardAdapter suggestionCardAdapter;
    SharedPreferences getPrefs;
    Cursor cursor = null;
    Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_suggestion);
        setSupportActionBar(toolbar);

        getPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String myLatitude = getPrefs.getString("latitude", "23.777785");
        String myLongitude = getPrefs.getString("longitude", "90.423011");

        myLocation = new Location(LocationManager.GPS_PROVIDER);
        myLocation.setLatitude(Double.parseDouble(myLatitude));
        myLocation.setLongitude(Double.parseDouble(myLongitude));





        spotsRecyclerView = (RecyclerView) findViewById(R.id.spot_recyclerview);

        allSpots = new ArrayList<SpotModel>();


        DatabaseHelper databaseHelper = new DatabaseHelper(Suggestion.this);
        try{
            databaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = databaseHelper.rawQuery("Select * From FamousSpot Natural Join SpotImage", null);
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

        suggestionCardAdapter = new SuggestionCardAdapter(allSpots, myLocation, Suggestion.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        spotsRecyclerView.setLayoutManager(linearLayoutManager);
        spotsRecyclerView.setAdapter(suggestionCardAdapter);



        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
