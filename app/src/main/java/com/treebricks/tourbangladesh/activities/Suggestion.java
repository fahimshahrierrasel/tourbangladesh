package com.treebricks.tourbangladesh.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.adapter.SuggestionCardAdapter;
import com.treebricks.tourbangladesh.database.DatabaseHelper;
import com.treebricks.tourbangladesh.model.SpotCardModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Suggestion extends AppCompatActivity {

    RecyclerView spotsRecyclerView;
    List<SpotCardModel> allSpots;
    SuggestionCardAdapter suggestionCardAdapter;

    Cursor cursor = null;

    private Drawer suggestionDrawer = null;
    private AccountHeader suggestionAccountHeader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_suggestion);
        setSupportActionBar(toolbar);

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

        allSpots = new ArrayList<SpotCardModel>();


        DatabaseHelper databaseHelper = new DatabaseHelper(Suggestion.this);
        try{
            databaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cursor = databaseHelper.rawQuery("Select * From FamousSpot Natural Join SpotImage Where District = ?", new String[] {"Dhaka"});
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                String spotName = cursor.getString(cursor.getColumnIndex("SpotName"));
                String spotImage = cursor.getString(cursor.getColumnIndex("ImageURL"));
                String spotDistrict = cursor.getString(cursor.getColumnIndex("District"));
                allSpots.add(new SpotCardModel(spotImage, spotName, spotDistrict, String.valueOf(i)+" km"));
                i++;
            } while (cursor.moveToNext());
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                databaseHelper.close();
            }
        }


        suggestionCardAdapter = new SuggestionCardAdapter(allSpots, Suggestion.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        spotsRecyclerView.setLayoutManager(linearLayoutManager);
        spotsRecyclerView.setAdapter(suggestionCardAdapter);


    }
}
