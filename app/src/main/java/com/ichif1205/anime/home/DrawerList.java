package com.ichif1205.anime.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class DrawerList extends RecyclerView {
    public DrawerList(Context context) {
        super(context);
        init(context);
    }

    public DrawerList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawerList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(context));
    }
}
