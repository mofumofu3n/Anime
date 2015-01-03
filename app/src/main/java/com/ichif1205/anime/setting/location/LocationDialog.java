package com.ichif1205.anime.setting.location;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.ichif1205.anime.R;
import com.ichif1205.anime.setting.SettingPreference;

public class LocationDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getActivity();
        final String[] locationNames = context.getResources().getStringArray(R.array.lineup_name);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("放送地域を選択してください");
        builder.setItems(locationNames, createOnClickListener(context, locationNames));
        return builder.create();
    }

    private DialogInterface.OnClickListener createOnClickListener(final Context context, final String[] locationNames) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String[] locationIds = context.getResources().getStringArray(R.array.lineup_id);
                final SettingPreference pref = new SettingPreference(context);
                pref.setLocationId(locationIds[which]);
                pref.setLocationName(locationNames[which]);

                final Fragment target = getTargetFragment();

                // 地域変更を通知
                if (target instanceof OnChangeListener) {
                    ((OnChangeListener) target).onChangeLocation();
                }
            }
        };
    }

    public interface OnChangeListener {
        void onChangeLocation();
    }
}
