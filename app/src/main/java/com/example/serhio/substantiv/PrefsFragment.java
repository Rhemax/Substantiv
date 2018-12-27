package com.example.serhio.substantiv;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;


public class PrefsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        treamVisible(3);

    }

    //Hide the parameters those must not be visible in Settings layout
    //#visiblePreferencesCount - the count of Preferences that must be visible (starting with the first #visiblePreferencesCount from preferences.xml
    private void treamVisible(int visiblePreferencesCount) {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        visiblePreferencesCount--;
        int preferencesCount = preferenceScreen.getPreferenceCount() - 1;
        while (preferencesCount != visiblePreferencesCount) {
            Preference preference = preferenceScreen.getPreference(preferencesCount--);
            preference.setVisible(false);
        }
    }

}