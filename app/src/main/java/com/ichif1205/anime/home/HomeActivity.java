package com.ichif1205.anime.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.ichif1205.anime.R;
import com.ichif1205.anime.article.ArticleFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeActivity extends ActionBarActivity implements DrawerAdapter.OnItemClickListener {

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private DrawerAdapter mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        setContentView(R.layout.activity_home);

        ButterKnife.inject(this);
        setupDrawerList();
        setupActionBar();

        if (savedInstanceState == null) {
            setupArticleFragment();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerList() {
        mAdapter = new DrawerAdapter(this);
        mAdapter.setOnItemClickListener(this);

        final DrawerList drawerList = (DrawerList) findViewById(R.id.left_drawer);
        drawerList.setAdapter(mAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupArticleFragment() {
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, new ArticleFragment());
        transaction.commit();
    }

    @Override
    public void onItemClick(int position) {
        final Fragment fragment = mAdapter.getItem(position);
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        final FragmentManager manager = getSupportFragmentManager();

        final FragmentTransaction transition = manager.beginTransaction();
        transition.replace(R.id.container, fragment);
        transition.commit();
        mDrawerLayout.closeDrawers();
    }
}
