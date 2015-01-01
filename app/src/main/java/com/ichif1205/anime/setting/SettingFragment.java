package com.ichif1205.anime.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ichif1205.anime.R;
import com.ichif1205.anime.setting.location.LocationDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingFragment extends Fragment implements Adapter.OnItemClickListener {
    private static final String TAG = SettingPreference.class.getSimpleName();

    @InjectView(R.id.setting_list)
    SettingListView mListView;

    private Adapter mAdapter;

    private static final SparseArray<String> DATASET = new SparseArray<>();

    static {
        DATASET.append(Adapter.TYPE_LOCATION, "地域設定");
        DATASET.append(Adapter.TYPE_USAGE, "使い方");
        DATASET.append(Adapter.TYPE_LICENSE, "著作権情報");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.inject(this, view);

        mAdapter = new Adapter(DATASET);
        mAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onUsageClick() {

    }

    @Override
    public void onLocationClick() {
        final FragmentManager manager = getFragmentManager();
        final DialogFragment fragment = new LocationDialog();
        fragment.show(manager, TAG);
    }

    @Override
    public void onLicenseClick() {

    }
}
