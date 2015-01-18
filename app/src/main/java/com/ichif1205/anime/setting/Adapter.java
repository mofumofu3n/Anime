package com.ichif1205.anime.setting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ichif1205.anime.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TYPE_LOCATION = 0;
    static final int TYPE_USAGE = 1;
    static final int TYPE_LICENSE = 2;

    private final SparseArray<String> mDataset;
    private OnItemClickListener mListener;

    public Adapter(SparseArray<String> dataset) {
        mDataset = dataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.layout_setting_item, parent, false);

        if (viewType == TYPE_LOCATION) {
            return new LocationHolder(view);
        }

        if (viewType == TYPE_USAGE) {
            return new UsageHolder(view);
        }

        if (viewType == TYPE_LICENSE) {
            return new LicenseHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int type = getItemViewType(position);
        final String title = mDataset.get(type);

        if (type == TYPE_USAGE) {
            ((UsageHolder) holder).bind(title);
            return;
        }

        if (type == TYPE_LOCATION) {
            ((LocationHolder) holder).bind(title);
            return;
        }

        if (type == TYPE_LICENSE) {
            ((LicenseHolder) holder).bind(title);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.keyAt(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    interface OnItemClickListener {
        void onUsageClick();

        void onLocationClick();

        void onLicenseClick();
    }

    public class UsageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.title)
        TextView titleView;


        public UsageHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bind(String title) {
            titleView.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) {
                return;
            }
            mListener.onUsageClick();
        }
    }

    public class LocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.title)
        TextView titleView;

        public LocationHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bind(String title) {
            final Context context = titleView.getContext();

            final SettingPreference pref = new SettingPreference(context);
            final String location = pref.getLocationName();

            if (TextUtils.isEmpty(location)) {
                titleView.setText(title);
            } else {
                titleView.setText(location);
            }
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) {
                return;
            }
            mListener.onLocationClick();
        }
    }

    public class LicenseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.title)
        TextView titleView;

        public LicenseHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bind(String title) {
            titleView.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) {
                return;
            }
            mListener.onLicenseClick();
        }
    }
}
