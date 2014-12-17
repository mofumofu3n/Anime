package com.ichif1205.anime.browser;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import com.ichif1205.anime.R;
import com.ichif1205.anime.model.Article;

public class BrowserActivity extends ActionBarActivity {

    static final String EXTRA_TITLE = "extra_title";
    static final String EXTRA_URL = "extra_url";

    private BrowserFragment mFragment;

    private String mUrl = "";
    private String mTitle = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        final Intent intent = getIntent();
        mUrl = intent.getStringExtra(EXTRA_URL);
        mTitle = intent.getStringExtra(EXTRA_TITLE);


        mFragment = BrowserFragment.newInstance(mUrl);

        getSupportFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
//        setActionBar(getActionBar());
    }

    private void setActionBar(ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser, menu);
//        final ShareActionProvider provider = (ShareActionProvider) menu.findItem(R.id.action_share).getActionProvider();
//        provider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
//        provider.setShareIntent(doShare());


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private Intent doShare() {
//        String text = String.format(SHARE_TEXT, mTitle, mUrl);
        final String text = "";

        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        return intent;
    }

    public static void start(Activity activity, Article item) {
        final Intent intent = new Intent(activity, BrowserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_TITLE, item.title);
        intent.putExtra(EXTRA_URL, item.url);
        activity.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mFragment.back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
