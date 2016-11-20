package com.treebricks.tourbangladesh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.treebricks.tourbangladesh.R;
import com.treebricks.tourbangladesh.activities.About;

/**
 * Created by fahim on 11/20/16.
 */

public class SettingsFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        String key = preference.getKey();
        if(key.equals("developer"))
        {
            Intent info = new Intent(getActivity(), About.class);
            startActivity(info);
        }

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
