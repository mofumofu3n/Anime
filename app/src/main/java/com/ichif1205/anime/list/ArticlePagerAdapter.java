package com.ichif1205.anime.list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ArticlePagerAdapter extends FragmentPagerAdapter {
    private static final String[] TITLES = {"新着", "人気"};

    public ArticlePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NewArticleFragment();
        } else {
            return new TodayArticleFragment();
        }
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
