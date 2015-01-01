package com.ichif1205.anime.setting.location;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.ichif1205.anime.R;
import com.ichif1205.anime.setting.SettingPreference;

public class LocationDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getActivity();
        final String[] locationNames = context.getResources().getStringArray(R.array.lineup_key);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("放送地域を選択してください");
        builder.setItems(locationNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String[] locations = context.getResources().getStringArray(R.array.lineup_query);
                final SettingPreference pref = new SettingPreference(context);
                pref.setLocationId(locations[which]);
                pref.setLocationName(locationNames[which]);

            }
        });
        return builder.create();
    }
}
