package com.ichif1205.anime.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichif1205.anime.R;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String[] mDataset;
    private OnItemClickListener mListener;

    public DrawerAdapter(String[] dataset) {
        mDataset = dataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater vi = LayoutInflater.from(parent.getContext());
        final View v = vi.inflate(R.layout.layout_drawer_item, parent, false);
        final TextView tv = (TextView) v.findViewById(R.id.title);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setTitle(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String title);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTitleView;

        public ViewHolder(TextView v) {
            super(v);
            mTitleView = v;
            mTitleView.setOnClickListener(this);
        }

        public void setTitle(String title) {
            mTitleView.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) {
                return;
            }

            mListener.onItemClick((String) (mTitleView.getText()));
        }
    }
}
