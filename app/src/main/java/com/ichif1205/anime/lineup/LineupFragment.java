package com.ichif1205.anime.lineup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ichif1205.anime.R;
import com.ichif1205.anime.setting.SettingPreference;
import com.ichif1205.anime.setting.location.LocationDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LineupFragment extends Fragment implements LocationDialog.OnChangeListener {
    private static final String[] DUMMY = {
            "ダミー1",
            "ダミー2",
            "ダミー3",
            "ダミー4",
            "ダミー5"
    };

    @InjectView(R.id.lineup_list)
    public RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lineup, container, false);
        ButterKnife.inject(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final Adapter adapter = new Adapter(DUMMY);
        mRecyclerView.setAdapter(adapter);

        setupTitle();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isSettingLocation()) {
            showSettingLocationFragment();
            return;
        }
    }

    private void setupTitle() {
        final ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("番組表");
    }

    /**
     * 位置情報が設定済みか
     *
     * @return 設定済みの場合、true
     */
    private boolean isSettingLocation() {
        final Context context = getActivity();

        final SettingPreference pref = new SettingPreference(context);
        return !TextUtils.isEmpty(pref.getLocationId());
    }

    private void showSettingLocationFragment() {
        final FragmentManager manager = getFragmentManager();
        final DialogFragment fragment = new LocationDialog();
        fragment.setTargetFragment(this, -1);
        fragment.show(manager, LocationDialog.TAG);
    }

    @Override
    public void onChangeLocation() {
       
    }
}
