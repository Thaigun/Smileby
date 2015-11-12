package com.possedev.smileby.structures;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.possedev.smileby.R;

/**
 * Created by Antti on 12.11.2015.
 */
public class AppSettings extends Application {
    SharedPreferences preferences;

    public AppSettings() {
        preferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public String getPreferenceValueString(String key) {
        return preferences.getString(key, "");
    }


}
