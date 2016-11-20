package com.treebricks.tourbangladesh.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.treebricks.tourbangladesh.R;

public class About extends AppCompatActivity {

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
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
