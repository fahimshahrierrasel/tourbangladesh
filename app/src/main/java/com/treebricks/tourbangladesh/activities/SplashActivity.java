package com.treebricks.tourbangladesh.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.thunderrise.animations.PulseAnimation;
import com.treebricks.tourbangladesh.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView pulseImage = (ImageView)findViewById(R.id.logo);

        PulseAnimation.create().with(pulseImage)
                .setDuration(500)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();
    }
    @Override
    public void onStart() {
        super.onStart();
        int SPLASH_TIME_OUT = 1500;
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent applicaitonHome = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(applicaitonHome);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
