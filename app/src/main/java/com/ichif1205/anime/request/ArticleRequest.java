package com.ichif1205.anime.request;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleRequest extends JsonArrayRequest {

    public ArticleRequest(String url) {
        super(url, null, null);
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        final ArrayList<Article> articleList = new ArrayList<>();

        final int length = response.length();
        for (int i = 0; i < length; i++) {
            try {
                final JSONObject row = response.getJSONObject(i);
                final Article article = Article.parse(row);
                articleList.add(article);
            } catch (JSONException e) {
                deliverError(new VolleyError(e));
                return;
            }
        }
        BusHolder.get().post(new SuccessEvent(articleList));
    }

    @Override
    public void deliverError(VolleyError error) {
        BusHolder.get().post(new ErrorEvent(error));
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
        private final VolleyError mError;

        public ErrorEvent(VolleyError error) {
            mError = error;
        }

        public VolleyError getError() {
            return mError;
        }
    }
}
