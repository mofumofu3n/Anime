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

import com.android.volley.RequestQueue;
import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.R;
import com.ichif1205.anime.model.Lineup;
import com.ichif1205.anime.request.LineupRequest;
import com.ichif1205.anime.request.RequestManager;
import com.ichif1205.anime.setting.SettingPreference;
import com.ichif1205.anime.setting.location.LocationDialog;
import com.squareup.otto.Subscribe;

import java.util.List;

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

    private Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lineup, container, false);
        ButterKnife.inject(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);

        setupTitle();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final Context context = getActivity();
        final SettingPreference pref = new SettingPreference(context);

        if (!isSettingLocation(pref)) {
            showSettingLocationFragment();
            return;
        }
        request(context, pref);
        BusHolder.get().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusHolder.get().unregister(this);
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
    private boolean isSettingLocation(SettingPreference pref) {
        return !TextUtils.isEmpty(pref.getLocationId());
    }

    private void showSettingLocationFragment() {
        final FragmentManager manager = getFragmentManager();
        final DialogFragment fragment = new LocationDialog();
        fragment.setTargetFragment(this, -1);
        fragment.show(manager, LocationDialog.TAG);
    }

    private void request(Context context, SettingPreference pref) {
        final LineupRequest request = new LineupRequest(String.format("http://192.168.33.10/anime/lineup/location/%s", pref.getLocationId()));
        getRequestQueue(getActivity()).add(request);
    }

    private RequestQueue getRequestQueue(Context context) {
        return RequestManager.getInstance().getRequestQueue(context);
    }

    @Override
    public void onChangeLocation() {

    }

    @Subscribe
    public void onResponse(LineupRequest.SuccessEvent event) {
        final List<Lineup> list = event.getList();
        mAdapter.add(list);
        mAdapter.notifyDataSetChanged();
    }

    public void onError(LineupRequest.ErrorEvent event) {

    }
}
