package com.ichif1205.anime.lineup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichif1205.anime.R;
import com.ichif1205.anime.model.Lineup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Lineup> mDataset;

    Adapter() {
        mDataset = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        final View view = inflater.inflate(R.layout.layout_lineup_item, parent, false);

        return new LineupItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LineupItem) holder).bind(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(List<Lineup> list) {
        mDataset.addAll(list);
    }

    public class LineupItem extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView mTitleView;

        public LineupItem(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bind(Lineup lineup) {
            mTitleView.setText(lineup.title);
        }
    }
}
