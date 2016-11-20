package com.treebricks.tourbangladesh.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.database.DatabaseHelper;
import com.treebricks.tourbangladesh.model.SpotModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences getPrefs;
    Cursor cursor = null;
    LatLng myLocation;
    List<LatLng> allLocations;
    List<String> allSpots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        allLocations = new ArrayList<LatLng>();
        allSpots = new ArrayList<String>();

        getPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String myLatitude = getPrefs.getString("latitude", "23.777785");
        String myLongitude = getPrefs.getString("longitude", "90.423011");

        myLocation = new LatLng(Double.parseDouble(myLatitude), Double.parseDouble(myLongitude));

        DatabaseHelper databaseHelper = new DatabaseHelper(MapActivity.this);
        try{
            databaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = databaseHelper.rawQuery("Select * From FamousSpot", null);
        if (cursor.moveToFirst()) {
            do {

                String spotName = cursor.getString(cursor.getColumnIndex("SpotName"));
                String spotLatitude = cursor.getString(cursor.getColumnIndex("Latitude"));
                String spotLongitude = cursor.getString(cursor.getColumnIndex("Longitude"));
                LatLng spotLocation = new LatLng(Double.parseDouble(spotLatitude), Double.parseDouble(spotLongitude));
                allLocations.add(spotLocation);
                allSpots.add(spotName);

            } while (cursor.moveToNext());
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                databaseHelper.close();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions()
                .position(myLocation)
                .title("Your Location")
                .snippet("This is your Location. Isn't it?")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person))
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 9f));

        for(int i = 0; i < allLocations.size(); i++)
        {
            mMap.addMarker(new MarkerOptions().position(allLocations.get(i)).title(allSpots.get(i)));
        }

    }
}
