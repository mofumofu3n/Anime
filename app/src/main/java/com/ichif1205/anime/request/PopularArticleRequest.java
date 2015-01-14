package com.ichif1205.anime.request;

import com.ichif1205.anime.ArticleParser;
import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.model.Article;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

public class PopularArticleRequest extends AbsArticleRequest {
    private static final String CLASS_NAME = "Article";
    private static final String COL_COUNT = "click";

    public PopularArticleRequest() {
        super(CLASS_NAME, COL_COUNT);
    }

    @Override
    protected FindCallback<ParseObject> createCallback(final boolean isPaging) {
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
