package com.treebricks.tourbangladesh.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.database.DatabaseHelper;
import com.treebricks.tourbangladesh.model.SpotModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements LocationListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    TextView coordinates;
    TextView userAddress;
    ImageView suggestedCardImage;
    TextView suggestedSpotName;
    TextView suggestedLocation;
    TextView suggestedDistance;

    String latitudeString;
    String longitudeString;
    LocationManager locationManager;
    String provider;
    String lalbaghFort1 = "http://i.imgur.com/SAvQ8N6.jpg";
    String lalbaghFort2 = "http://i.imgur.com/N0XX15z.jpg";
    ImageView backdrop;
    CountDownTimer countDownTimer;
    Location location;
    Location myLocation;
    SharedPreferences getPrefs;
    Criteria criteria;
    Cursor cursor = null;

    Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                boolean isFirstRun = getPrefs.getBoolean("first_run", true);
                if (isFirstRun) {
                    copyDatabase("FamousSpots.db");
                    SharedPreferences.Editor editor = getPrefs.edit();
                    editor.putBoolean("first_run", false);
                    editor.putString("parameter", "5");
                    editor.apply();
                }
            }
        });
        t.start();
        setContentView(R.layout.activity_main);

        backdrop = (ImageView) findViewById(R.id.backdrop);
        suggestedCardImage = (ImageView) findViewById(R.id.suggested_card_image);
        coordinates = (TextView) findViewById(R.id.coordinate);
        userAddress = (TextView) findViewById(R.id.location);
        suggestedSpotName = (TextView) findViewById(R.id.suggested_spot_name);
        suggestedLocation = (TextView) findViewById(R.id.suggested_card_location);
        suggestedDistance = (TextView) findViewById(R.id.suggested_card_distance);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 111);

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Permission Needed", "Rationale");
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 111);
            }
        }
        else{
            provider = locationManager.getBestProvider(criteria, false);
            location = locationManager.getLastKnownLocation(provider);
        }

        // Navigation Drawer Header
        AccountHeader homePageAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bangladesh)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .build();
        // Navigation Drawer
        final Drawer homePageDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(homePageAccountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(R.drawable.idea).withName("Spot Suggestion").withIdentifier(1),
                        new PrimaryDrawerItem().withIcon(R.drawable.find_map).withName("Find Spot").withIdentifier(2),
                        new PrimaryDrawerItem().withIcon(R.drawable.map).withName("Live Tour Map").withIdentifier(3),
                        new PrimaryDrawerItem().withIcon(R.drawable.fire_alarm).withName("Emergency").withIdentifier(4)
                )
                .addStickyDrawerItems(
                        new PrimaryDrawerItem().withIcon(R.drawable.settings).withName("Settings").withIdentifier(5)
                )
                .build();

        homePageDrawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                if (drawerItem != null) {
                    switch ((int) drawerItem.getIdentifier()) {
                        case 1: {
                            Intent suggestion = new Intent(MainActivity.this, Suggestion.class);
                            startActivity(suggestion);
                            homePageDrawer.closeDrawer();
                            break;
                        }
                        case 2: {
                            Intent spotFinder = new Intent(MainActivity.this, SpotFinder.class);
                            startActivity(spotFinder);
                            homePageDrawer.closeDrawer();
                            break;
                        }
                        case 3: {
                            Intent liveTourMap = new Intent(MainActivity.this, MapActivity.class);
                            startActivity(liveTourMap);
                            homePageDrawer.closeDrawer();
                            break;
                        }
                        case 4: {
                            Intent emergency = new Intent(MainActivity.this, Emergency.class);
                            startActivity(emergency);
                            homePageDrawer.closeDrawer();
                            break;
                        }
                        case 5: {
                            Intent settings = new Intent(MainActivity.this, Settings.class);
                            startActivity(settings);
                            homePageDrawer.closeDrawer();
                            break;
                        }
                    }
                }

                return true;
            }
        });

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

        latitudeString = getPrefs.getString("latitude", "00.0000000");
        longitudeString = getPrefs.getString("longitude", "00.0000000");

        myLocation = new Location(LocationManager.GPS_PROVIDER);
        myLocation.setLatitude(Double.parseDouble(latitudeString));
        myLocation.setLongitude(Double.parseDouble(longitudeString));


        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            coordinates.setText(latitudeString + ", " + longitudeString);
        }


        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        try{
            databaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        random = new Random();

        int randomNumber = random.nextInt(61);


        cursor = databaseHelper.rawQuery("Select * From FamousSpot Natural Join SpotImage Where SpotID = ?", new String[]{String.valueOf(randomNumber)});
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

                Location userLocation = new Location(LocationManager.GPS_PROVIDER);
                userLocation.setLatitude(Double.parseDouble(spotLatitude));
                userLocation.setLongitude(Double.parseDouble(spotLongitude));

                float distance = userLocation.distanceTo(myLocation) / 1000;

                SpotModel spotModel = new SpotModel(spotId,spotName, spotCatagory,
                        spotLocation, spotDistrict, spotDescription, spotLatitude, spotLongitude, spotImage);

                Glide.with(MainActivity.this).load(spotModel.getSpotImageUrl()).into(suggestedCardImage);
                suggestedSpotName.setText(spotModel.getSpotName());
                suggestedLocation.setText(spotModel.getSpotLocation());
                suggestedDistance.setText(String.format(Locale.US, "Distance %.2f km", distance));


            } while (cursor.moveToNext());
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                databaseHelper.close();
            }
        }

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

        SharedPreferences.Editor preferenceEditor = getPrefs.edit();
        preferenceEditor.putString("latitude", String.format(Locale.US, "%.7f", lat));
        preferenceEditor.putString("longitude", String.format(Locale.US, "%.7f", lng));
        preferenceEditor.apply();

        coordinates.setText(getPrefs.getString("latitude", "00.0000000") + ", " + getPrefs.getString("longitude", "00.0000000"));


        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(", ");
                builder.append(addressStr);
            }
            String fnialAddress = builder.toString(); //This is the complete address.
            userAddress.setText(fnialAddress); //This will display the final address.


        } catch (IOException | NullPointerException e) {
            // Handle IOException
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        countDownTimer.cancel();
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 111)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    provider = locationManager.getBestProvider(criteria, false);
                    location = locationManager.getLastKnownLocation(provider);
                    locationManager.requestLocationUpdates(provider, 400, 1, this);
                    onLocationChanged(location);
                }

            }
            else {
                Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showExplanation(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 111);
                    }
                });
        builder.create().show();
    }

    private void copyDatabase(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream input = null;
        OutputStream output = null;
        try {
            Log.i(TAG, "Copying Database " + filename);
            input = assetManager.open(filename);
            File DatabaseDirectory = new File(getBaseContext().getApplicationInfo().dataDir + "/databases");
            if (!DatabaseDirectory.exists()) {
                DatabaseDirectory.mkdirs();
                Log.i(TAG, "Directory Created" + DatabaseDirectory);
            }

            output = new FileOutputStream(DatabaseDirectory + "/" + filename);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in copying Database: " + filename);
            Log.e(TAG, "Exception in copying Database Error: " + e.toString());
        } finally {
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
