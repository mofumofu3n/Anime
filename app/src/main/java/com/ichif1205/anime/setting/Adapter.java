package com.ichif1205.anime.setting;

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

    public Adapter(String[] dataset) {
        mDataset = dataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        final View view = inflater.inflate(R.layout.layout_setting, parent, false);

        return new Usage(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Usage) holder).bind(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class Usage extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.title)
        TextView titleView;


        public Usage(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bind(String title) {
            titleView.setText(title);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
