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

    private static final String CLASS_NAME = "Article";
    private static final String COL_PUBLISHED_AD = "publishedAt";

    private ParseQuery<ParseObject> mQuery;

    public ParseArticleRequest() {
        mQuery = ParseQuery.getQuery(CLASS_NAME);
        mQuery.setMaxCacheAge(MAX_CACHE_TIME_MILLIS);
    }

    public void find() {
        mQuery.orderByDescending(COL_PUBLISHED_AD);

        if (mQuery.hasCachedResult()) {
            mQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_ONLY);
        } else {
            mQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ONLY);
        }

        // キャッシュから読み込み後、APIアクセスし、結果を返す
        mQuery.findInBackground(createCallback());
    }

    private FindCallback<ParseObject> createCallback() {
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
                BusHolder.get().post(new SuccessEvent(articleList));
            }
        };
    }

    public class SuccessEvent {
        private final List<Article> mList;

        public SuccessEvent(List<Article> list) {
            mList = list;
        }

        public List<Article> getList() {
            return mList;
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
