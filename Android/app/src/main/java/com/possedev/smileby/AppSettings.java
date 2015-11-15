package com.possedev.smileby;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by Antti on 12.11.2015.
 */
public class AppSettings {
    SharedPreferences preferences;

    public AppSettings(Context context) {
        //TODO: make this work: String fileName = getResources().getString(R.string.preference_file_key);
        preferences = context.getSharedPreferences("com.possedev.smileby.USER_SETTINGS", Context.MODE_PRIVATE);
        preferences.edit().commit();
        if (getUsername().isEmpty()) {
            Random random = new Random();
            //setUsername("User" + random.nextInt(2000));
            setUsername("anabanana");
        }
    }

    public String getPreferenceValueString(String key) {
        return preferences.getString(key, "");
    }

    public void setPreferencesValueString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getUsername() {
        return getPreferenceValueString("username");
    }

    public void setUsername(String name) {
        setPreferencesValueString("username", name);
    }


}
