package com.possedev.smileby;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by Antti on 12.11.2015.
 */
public class AppSettings extends Application {
    SharedPreferences preferences;

    public AppSettings() {
        preferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (getUsername().isEmpty()) {
            Random random = new Random();
            setUsername("User" + random.nextInt(2000));
        }
    }

    public String getPreferenceValueString(String key) {
        return preferences.getString(key, "");
    }

    public void setPreferencesValueString(String key, String value) {
        preferences.edit().putString(key, value);
    }

    public String getUsername() {
        return getPreferenceValueString("username");
    }

    public void setUsername(String name) {
        setPreferencesValueString("username", name);
    }


}
