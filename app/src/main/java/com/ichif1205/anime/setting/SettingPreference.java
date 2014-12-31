package com.ichif1205.anime.setting;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingPreference {
    private static final String KEY = "pref_setting";

    private static final String KEY_LOCATION = "location";

    private final SharedPreferences mPref;


    public SettingPreference(Context context) {
        mPref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public void setLocation(String location) {
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putString(KEY_LOCATION, location);
        editor.commit();
    }

    public String getLocation() {
        return mPref.getString(KEY_LOCATION, null);
    }
}
