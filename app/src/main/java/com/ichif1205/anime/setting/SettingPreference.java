package com.ichif1205.anime.setting;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingPreference {
    private static final String KEY = "pref_setting";

    private static final String KEY_LOCATION_ID = "location_id";
    private static final String KEY_LOCATION_NAME = "location_name";

    private final SharedPreferences mPref;


    public SettingPreference(Context context) {
        mPref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public void setLocationId(String locationId) {
        putString(KEY_LOCATION_ID, locationId);
    }

    public String getLocationId() {
        return mPref.getString(KEY_LOCATION_ID, null);
    }

    public void setLocationName(String locationName) {
        putString(KEY_LOCATION_NAME, locationName);
    }

    public String getLocationName() {
        return mPref.getString(KEY_LOCATION_NAME, null);
    }

    private void putString(String key, String value) {
        final SharedPreferences.Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
