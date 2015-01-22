package com.ichif1205.anime.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.ichif1205.anime.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ArticleFragment extends Fragment {
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip mTabs;

    @InjectView(R.id.pager)
    ViewPager mPager;

    public ArticleFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.inject(this, view);

        final ArticlePagerAdapter adapter = new ArticlePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        mPager.setPageMargin(pageMargin);
        mTabs.setViewPager(mPager);
        return view;
    }


    public static class ArticlePagerAdapter extends FragmentPagerAdapter {
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
}
