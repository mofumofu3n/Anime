package com.ichif1205.anime.lineup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ichif1205.anime.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LineupFragment extends Fragment {
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

    private void setupTitle() {
        final ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("番組表");
    }
}
