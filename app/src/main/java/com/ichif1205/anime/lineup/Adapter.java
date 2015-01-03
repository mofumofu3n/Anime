package com.ichif1205.anime.lineup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichif1205.anime.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String[] mDataset;

    Adapter(String[] dataset) {
        mDataset = dataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        final View view = inflater.inflate(R.layout.layout_lineup_item, parent, false);

        return new LineupItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LineupItem) holder).bind(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class LineupItem extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView mTitleView;

        public LineupItem(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bind(String title) {
            mTitleView.setText(title);
        }
    }
}
