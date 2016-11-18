package com.treebricks.tourbangladesh.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.treebricks.tourbangladesh.R;

public class SpotFinder extends AppCompatActivity {

    private Drawer spotFinderDrawer = null;
    private AccountHeader spotFinderAccountHeader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_finder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_spot_finder);
        setSupportActionBar(toolbar);

        // Navigation Drawer Header
        spotFinderAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.minimalistic_city)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .build();
        // Navigation Drawer
        spotFinderDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(spotFinderAccountHeader)
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
                                    Intent home = new Intent(SpotFinder.this, MainActivity.class);
                                    startActivity(home);
                                    finish();
                                    break;
                                }
                                case 2:
                                {
                                    Intent suggestion = new Intent(SpotFinder.this, Suggestion.class);
                                    startActivity(suggestion);
                                    finish();
                                    break;
                                }
                                case 3:
                                {

                                    Snackbar.make(view, "You are already on Spot Finder.", Snackbar.LENGTH_SHORT).show();
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
                                    Intent info = new Intent(SpotFinder.this, About.class);
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

    }
}
