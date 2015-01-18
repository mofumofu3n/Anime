package com.ichif1205.anime.home;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichif1205.anime.R;
import com.ichif1205.anime.browser.BrowserFragment;
import com.ichif1205.anime.lineup.LineupFragment;
import com.ichif1205.anime.article.ArticleFragment;
import com.ichif1205.anime.setting.SettingFragment;
import com.ichif1205.anime.twitter.TwitterFragment;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Resources mResources;

    private final SparseArray<Fragment> mDrawerList;

    private OnItemClickListener mListener;

    public DrawerAdapter(Context context) {
        mResources = context.getResources();

        mDrawerList = new SparseArray<>();
        initializedDrawerList();
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
        final int titleId = mDrawerList.keyAt(position);

        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setTitle(mResources.getString(titleId));
    }

    @Override
    public int getItemCount() {
        return mDrawerList.size();
    }

    public Fragment getItem(int position) {
        final int key = mDrawerList.keyAt(position);
        return mDrawerList.get(key);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private void initializedDrawerList() {
        mDrawerList.put(R.string.drawer_article, new ArticleFragment());
        mDrawerList.put(R.string.drawer_tvlineup, new LineupFragment());
        mDrawerList.put(R.string.drawer_twitter, new TwitterFragment());
        mDrawerList.put(R.string.drawer_new_anime, new BrowserFragment());
        mDrawerList.put(R.string.drawer_setting, new SettingFragment());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
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

            mListener.onItemClick(getPosition());
        }
    }
}
