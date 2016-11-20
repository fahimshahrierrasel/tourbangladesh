package com.treebricks.tourbangladesh.activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.treebricks.tourbangladesh.R;

import static android.R.id.message;

public class About extends AppCompatActivity {

    private Drawer aboutDrawer = null;
    private AccountHeader aboutAccountHeader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);

        Button aboutus = (Button) findViewById(R.id.about_us);
        Button appInfo = (Button) findViewById(R.id.why_tourbd);
        Button privacyPolicy = (Button) findViewById(R.id.privacy_policy);
        Button dataSource = (Button) findViewById(R.id.data_sources);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
                builder.setTitle("About Us")
                        .setMessage("Hello This is a test about us. Hello Again thank you!")
                        .setPositiveButton("OK", null);
                builder.create().show();
            }
        });

        appInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
                builder.setTitle("Why Tour Bangladesh?")
                        .setMessage("Hello This is a test about us. Hello Again thank you!")
                        .setPositiveButton("OK", null);
                builder.create().show();
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
                builder.setTitle("Privacy Policy")
                        .setMessage("Hello This is a test about us. Hello Again thank you!")
                        .setPositiveButton("OK", null);
                builder.create().show();
            }
        });

        dataSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
                builder.setTitle("Data Sources")
                        .setMessage("Hello This is a test about us. Hello Again thank you!")
                .setPositiveButton("OK", null);
                builder.create().show();
            }
        });

        // Navigation Drawer Header
        aboutAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.minimalistic_city)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .build();
        // Navigation Drawer
        aboutDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(aboutAccountHeader)
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
                                    Intent home = new Intent(About.this, MainActivity.class);
                                    startActivity(home);
                                    finish();
                                    break;
                                }
                                case 2:
                                {

                                    Intent info = new Intent(About.this, Suggestion.class);
                                    startActivity(info);
                                    finish();
                                    break;

                                }
                                case 3:
                                {
                                    Intent spotFinder = new Intent(About.this, SpotFinder.class);
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
                                    Snackbar.make(view, "You are already on App Info.", Snackbar.LENGTH_SHORT).show();
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
