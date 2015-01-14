package com.ichif1205.anime.request;

import com.ichif1205.anime.ArticleParser;
import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.model.Article;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ParseArticleRequest {
    /**
     * キャッシュ最大時間(10分)
     */
    private static final int MAX_CACHE_TIME_MILLIS = 10 * 60 * 1000;

    /**
     * 20件
     */
    private static final int QUERY_LIMIT = 20;

    private static final String CLASS_NAME = "Article";
    private static final String COL_PUBLISHED_AD = "publishedAt";

    private ParseQuery<ParseObject> mQuery;

    public ParseArticleRequest() {
        mQuery = ParseQuery.getQuery(CLASS_NAME);
        mQuery.setMaxCacheAge(MAX_CACHE_TIME_MILLIS);
        mQuery.orderByDescending(COL_PUBLISHED_AD);
    }

    public void find() {
        find(0, QUERY_LIMIT, false);
    }

    public void forceFind() {
        find(0, QUERY_LIMIT, false);
    }

    public void findPaging(int offset) {
        find(offset, QUERY_LIMIT, true);
    }

    private void find(int offset, int limit, boolean isPaging) {
        if (mQuery.hasCachedResult()) {
            // キャッシュがある場合はキャッシュから読み込む
            mQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ONLY);
        } else {
            mQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ONLY);
        }
        mQuery.setSkip(offset);
        mQuery.setLimit(limit);

        mQuery.findInBackground(createCallback(isPaging));
    }

    private FindCallback<ParseObject> createCallback(final boolean isPaging) {
        return new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e != null) {
                    BusHolder.get().post(new ErrorEvent(e));
                    return;
                }

                if (parseObjects == null) {
                    return;
                }

                final ArticleParser parser = new ArticleParser(parseObjects);
                final List<Article> articleList = parser.parse();
                BusHolder.get().post(new SuccessEvent(articleList, isPaging));
            }
        };
    }

    public class SuccessEvent {
        private final List<Article> mList;
        private final boolean mPaging;

        public SuccessEvent(List<Article> list, boolean isPaging) {
            mList = list;
            mPaging = isPaging;
        }

        public List<Article> getList() {
            return mList;
        }

        public boolean isPaging() {
            return mPaging;
        }
    }

    public class ErrorEvent {
        private final ParseException mError;

        public ErrorEvent(ParseException error) {
            mError = error;
        }

        public ParseException getError() {
            return mError;
        }
    }
}
