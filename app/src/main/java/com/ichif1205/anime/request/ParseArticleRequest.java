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
    private static final String CLASS_NAME = "Article";
    private static final String COL_PUBLISHED_AD = "publishedAt";


    public void find() {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(CLASS_NAME);
        query.orderByDescending(COL_PUBLISHED_AD);
        query.findInBackground(createCallback());
    }

    private FindCallback<ParseObject> createCallback() {
        return new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e != null) {
                    BusHolder.get().post(new ErrorEvent(e));
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
