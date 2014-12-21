package com.ichif1205.anime.setting;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class SettingListView extends RecyclerView {
    public SettingListView(Context context) {
        super(context);
        init(context);
    }

    public SettingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SettingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(context));
    }
}
