package com.ichif1205.anime.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ichif1205.anime.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingFragment extends Fragment {
    @InjectView(R.id.setting_list)
    SettingListView mListView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.inject(this, view);

        final String[] dataset = getResources().getStringArray(R.array.setting);
        final Adapter adapter = new Adapter(dataset);
        mListView.setAdapter(adapter);

        return view;

    }
}
