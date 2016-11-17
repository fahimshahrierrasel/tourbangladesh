package com.treebricks.tourbangladesh.activities;


import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dxtt.coolmenu.CoolMenuFrameLayout;
import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.fragments.AboutUsFragment;
import com.treebricks.tourbangladesh.fragments.DetailsFragment;
import com.treebricks.tourbangladesh.fragments.MainFragment;
import com.treebricks.tourbangladesh.fragments.SpotFinderFragment;
import com.treebricks.tourbangladesh.fragments.SuggestionFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    public static final String TAG = MainActivity.class.getSimpleName();

    CoolMenuFrameLayout coolMenuFrameLayout;

    List<Fragment> fragments = new ArrayList<>();

    List<String> titleList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                boolean isFirstRun = getPrefs.getBoolean("first_run", true);
                if (isFirstRun)
                {
                    copyDatabase("FamousSpots.db");
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("first_run", false);
                    e.apply();
                }
            }
        });
        t.start();
        setContentView(R.layout.activity_main);

        coolMenuFrameLayout = (CoolMenuFrameLayout) findViewById(R.id.rl_main);

        String[] titles = {"About Us", "Details Info", "Find Spot", "Spot Suggestion", "Tour Bangladesh" };

        titleList = Arrays.asList(titles);

        coolMenuFrameLayout.setTitles(titleList);

        fragments.add(new AboutUsFragment());
        fragments.add(new DetailsFragment());
        fragments.add(new SpotFinderFragment());
        fragments.add(new SuggestionFragment());
        fragments.add(new MainFragment());


        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        coolMenuFrameLayout.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
