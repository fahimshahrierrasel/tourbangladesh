package com.treebricks.tourbangladesh.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.database.DatabaseHelper;

import java.sql.SQLException;

public class Details extends AppCompatActivity implements OnMapReadyCallback {

    Cursor cursor = null;
    Toolbar toolbar;
    String spotName = "Lalbagh Fort";
    double latitude;
    double longitude;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        TextView spotdescription = (TextView) findViewById(R.id.spot_description);
        TextView spotdistrict = (TextView) findViewById(R.id.spot_district);
        TextView spotlocation = (TextView) findViewById(R.id.spot_location);
        ImageView spotimage = (ImageView) findViewById(R.id.spot_image);


        if(getIntent().getStringExtra("SPOT_NAME") != null)
        {
            spotName = getIntent().getStringExtra("SPOT_NAME");
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(Details.this);
        try {
            databaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = databaseHelper.rawQuery("Select * From FamousSpot Natural Join SpotImage Where SpotName = ?", new String[]{spotName});
        if (cursor.moveToFirst()) {
            do {
                String spotName = cursor.getString(cursor.getColumnIndex("SpotName"));
                String spotImage = cursor.getString(cursor.getColumnIndex("ImageURL"));
                String spotLocation = cursor.getString(cursor.getColumnIndex("SpotLocation"));
                String spotDistrict = cursor.getString(cursor.getColumnIndex("District"));
                String spotDescription = cursor.getString(cursor.getColumnIndex("SpotDescription"));
                latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Latitude")));
                longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Longitude")));

                Glide.with(Details.this).load(spotImage).into(spotimage);
                getSupportActionBar().setTitle(spotName);
                spotlocation.setText(spotLocation);
                spotdistrict.setText(spotDistrict);
                spotdescription.setText(spotDescription);

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

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng spotLocation = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(spotLocation).title(spotName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spotLocation, 16.5f));
        mMap.setMinZoomPreference(10f);
        mMap.setMaxZoomPreference(40f);


    }
}
