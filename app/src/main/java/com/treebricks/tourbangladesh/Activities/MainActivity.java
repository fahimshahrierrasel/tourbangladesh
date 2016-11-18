package com.treebricks.tourbangladesh.activities;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.holidaycheck.permissify.PermissifyConfig;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.treebricks.tourbangladesh.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView latitude;
    TextView longitude;
    TextView addressTextView;

    public static final String TAG = MainActivity.class.getSimpleName();

    LocationManager locationManager;
    String provider;
    String lalbaghFort1 = "http://i.imgur.com/SAvQ8N6.jpg";
    String lalbaghFort2 = "http://i.imgur.com/N0XX15z.jpg";
    PermissifyConfig permissifyConfig;
    ImageView backdrop;

    CountDownTimer countDownTimer;

    private Drawer homePageDrawer = null;
    private AccountHeader homePageAccountHeader = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                boolean isFirstRun = getPrefs.getBoolean("first_run", true);
                if (isFirstRun) {
                    copyDatabase("FamousSpots.db");
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("first_run", false);
                    e.apply();
                }
            }
        });
        t.start();
        setContentView(R.layout.activity_main);

        backdrop = (ImageView) findViewById(R.id.backdrop);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        addressTextView = (TextView) findViewById(R.id.address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Navigation Drawer Header
        homePageAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.visit_bangladesh)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .build();
        // Navigation Drawer
        homePageDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(homePageAccountHeader)
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
                                    //Intent home = new Intent(MainActivity.this, MainActivity.class);
                                    //startActivity(home);
                                    //finish();
                                    break;
                                }
                                case 2:
                                {
                                    Intent suggestion = new Intent(MainActivity.this, Suggestion.class);
                                    startActivity(suggestion);
                                    finish();
                                    break;
                                }
                                case 3:
                                {
                                    Intent spotFinder = new Intent(MainActivity.this, SpotFinder.class);
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
                                    Intent info = new Intent(MainActivity.this, About.class);
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


        final int[] flag = {0};

        countDownTimer = new CountDownTimer(30000, 5000) {

            public void onTick(long millisUntilFinished) {

                if (flag[0] == 0) {
                    Glide.with(MainActivity.this).load(lalbaghFort1).crossFade().into(backdrop);
                    flag[0] = 1;
                } else {
                    Glide.with(MainActivity.this).load(lalbaghFort2).crossFade().into(backdrop);
                    flag[0] = 0;
                }
            }

            public void onFinish() {
                countDownTimer.start();
            }
        };
        countDownTimer.start();


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


        }
        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latitude.setText("Location not available");
            longitude.setText("Location not available");
            System.out.println("Location not found!");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onLocationChanged(Location location) {

        //You had this as int. It is advised to have Lat/Loing as double.
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            String fnialAddress = builder.toString(); //This is the complete address.

            latitude.setText(String.valueOf(lat));
            longitude.setText(String.valueOf(lng));
            addressTextView.setText(fnialAddress); //This will display the final address.


        } catch (IOException e) {
            // Handle IOException
        } catch (NullPointerException e) {
            // Handle NullPointerException
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this, "Enabled new provider " + s,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this, "Disabled provider " + s,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onActivityCreated: OnActivityCreated");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    private void copyDatabase(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream input = null;
        OutputStream output = null;
        try {
            Log.i(TAG, "Copying Database "+filename);
            input = assetManager.open(filename);
            File DatabaseDirectory = new File(getBaseContext().getApplicationInfo().dataDir + "/databases");
            if(!DatabaseDirectory.exists())
            {
                DatabaseDirectory.mkdirs();
                Log.i(TAG,"Directory Created" + DatabaseDirectory);
            }

            output = new FileOutputStream(DatabaseDirectory+"/"+filename);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in copying Database: "+ filename);
            Log.e(TAG, "Exception in copying Database Error: "+ e.toString());
        }
        finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }
        }
    }

}
