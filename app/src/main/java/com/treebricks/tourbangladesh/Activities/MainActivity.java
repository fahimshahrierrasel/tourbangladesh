package com.treebricks.tourbangladesh.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import com.dxtt.coolmenu.CoolMenuFrameLayout;
import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.fragments.AboutUsFragment;
import com.treebricks.tourbangladesh.fragments.DetailsFragment;
import com.treebricks.tourbangladesh.fragments.MainFragment;
import com.treebricks.tourbangladesh.fragments.SpotFinderFragment;
import com.treebricks.tourbangladesh.fragments.SuggestionFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    CoolMenuFrameLayout coolMenuFrameLayout;

    List<Fragment> fragments = new ArrayList<>();

    List<String> titleList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
