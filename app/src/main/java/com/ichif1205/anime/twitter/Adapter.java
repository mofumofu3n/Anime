package com.ichif1205.anime.twitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichif1205.anime.R;
import com.ichif1205.anime.model.Twitter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Twitter> mDataset;

    public Adapter() {
        mDataset = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        final View itemView = inflater.inflate(R.layout.layout_twitter_item, parent, false);
        return new TwitterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TwitterHolder twitterHolder = (TwitterHolder) holder;
        twitterHolder.bind(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDataset == null || mDataset.size() == 0) {
            return 0;
        }
        return mDataset.size();
    }

    public void add(Twitter twitter) {
        mDataset.add(twitter);
    }

    public class TwitterHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.twitter_name)
        TextView mNameView;

        @InjectView(R.id.twitter_text)
        TextView mTextView;

        @InjectView(R.id.twitter_date)
        TextView mDateView;

        private TwitterHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        private void bind(Twitter twitter) {
            mNameView.setText(twitter.screenName);
            mTextView.setText(twitter.text);
            mDateView.setText(twitter.date);
        }
    }
}
