package com.ichif1205.anime.request;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public abstract class AbsArticleRequest {
    /**
     * キャッシュ最大時間(10分)
     */
    private static final int MAX_CACHE_TIME_MILLIS = 10 * 60 * 1000;

    /**
     * 20件
     */
    private static final int QUERY_LIMIT = 20;

    protected ParseQuery<ParseObject> mQuery;

    public AbsArticleRequest(String className, String orderBy) {
        mQuery = ParseQuery.getQuery(className);
        mQuery.setMaxCacheAge(MAX_CACHE_TIME_MILLIS);
        mQuery.orderByDescending(orderBy);
    }

    public void find() {
        if (mQuery.hasCachedResult()) {
            // キャッシュがある場合はキャッシュから読み込む
            mQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ONLY);
        } else {
            mQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ONLY);
        }
        find(0, QUERY_LIMIT, false);
    }

    public void forceFind() {
        mQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        find(0, QUERY_LIMIT, false);
    }

    public void findPaging(int offset) {
        if (mQuery.hasCachedResult()) {
            // キャッシュがある場合はキャッシュから読み込む
            mQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ONLY);
        } else {
            mQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ONLY);
        }
        find(offset, QUERY_LIMIT, true);
    }

    protected void find(int offset, int limit, boolean isPaging) {
        mQuery.setSkip(offset);
        mQuery.setLimit(limit);

        mQuery.findInBackground(createCallback(isPaging));
    }

    protected abstract FindCallback<ParseObject> createCallback(final boolean isPaging);
}
