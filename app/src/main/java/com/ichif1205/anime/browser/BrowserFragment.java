package com.ichif1205.anime.browser;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ichif1205.anime.R;

public class BrowserFragment extends Fragment {

    private String mUrl;
    private WebView mWebView;
    private ProgressBar mProgress;

    public static BrowserFragment newInstance(String url) {
        final BrowserFragment fragment = new BrowserFragment();
        final Bundle args = new Bundle();
        args.putString(BrowserActivity.EXTRA_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    public BrowserFragment() {
        setRetainInstance(true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(BrowserActivity.EXTRA_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_browser, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.web_view);
        setWebView(mWebView.getSettings());
        mWebView.setWebViewClient(mClient);
        mWebView.setWebChromeClient(mChrome);

        mProgress = (ProgressBar) rootView.findViewById(R.id.loading);

        return rootView;
    }

    /**
     * WebSettingsの設定
     *
     * @param settings
     */
    private void setWebView(WebSettings settings) {
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWebView.loadUrl(mUrl);
    }

    private WebViewClient mClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgress.setVisibility(View.GONE);
        }
    };

    private WebChromeClient mChrome = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgress.setProgress(newProgress);
        }
    };

    /**
     * 前のページに戻る
     */
    void back() {
        if (mWebView == null) {
            getActivity().finish();
            return;
        }

        if (!mWebView.canGoBack()) {
            getActivity().finish();
            return;
        }
        mWebView.goBack();
    }

}
