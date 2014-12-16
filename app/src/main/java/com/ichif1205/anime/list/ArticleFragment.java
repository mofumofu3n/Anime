package com.ichif1205.anime.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.inject(this, view);

        final ArticlePagerAdapter adapter = new ArticlePagerAdapter(getActivity().getSupportFragmentManager());

        mPager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        mPager.setPageMargin(pageMargin);
        mTabs.setViewPager(mPager);

        return view;
    }
}
