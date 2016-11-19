package com.treebricks.tourbangladesh.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
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
    private Drawer suggestionDrawer = null;
    private AccountHeader suggestionAccountHeader = null;

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


        // Navigation Drawer Header
        suggestionAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.minimalistic_city)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .build();
        // Navigation Drawer
        suggestionDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(suggestionAccountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(R.drawable.home).withName("Home").withIdentifier(1),
                        new PrimaryDrawerItem().withIcon(R.drawable.idea).withName("Spot Suggestion").withIdentifier(2),
                        new PrimaryDrawerItem().withIcon(R.drawable.find_map).withName("Find Spot").withIdentifier(3),
                        new PrimaryDrawerItem().withIcon(R.drawable.map).withName("Live Map").withIdentifier(4)
                )
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withIcon(R.drawable.settings).withName("Settings").withIdentifier(5),
                        new PrimaryDrawerItem().withIcon(R.drawable.info).withName("Info").withIdentifier(6)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem != null)
                        {
                            switch ((int) drawerItem.getIdentifier())
                            {
                                case 1:
                                {
                                    Intent home = new Intent(Suggestion.this, MainActivity.class);
                                    startActivity(home);
                                    finish();
                                    break;
                                }
                                case 2:
                                {
                                    Snackbar.make(view, "You are already on Spot Suggestion.", Snackbar.LENGTH_SHORT).show();
                                    break;
                                }
                                case 3:
                                {
                                    Intent spotFinder = new Intent(Suggestion.this, SpotFinder.class);
                                    startActivity(spotFinder);
                                    finish();
                                    break;
                                }
                                case 4:
                                {
                                    //Intent liveMap = new Intent(MainActivity.this, Suggestion.class);
                                    //startActivity(liveMap);
                                    break;
                                }
                                case 5:
                                {
                                    //Intent settings = new Intent(MainActivity.this, Suggestion.class);
                                    //startActivity(settings);
                                    break;
                                }
                                case 6:
                                {
                                    Intent info = new Intent(Suggestion.this, About.class);
                                    startActivity(info);
                                    finish();
                                    break;
                                }

                            }
                        }
                        return true;
                    }
                })
                .build();


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


    }
}
