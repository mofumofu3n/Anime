package com.ichif1205.anime.request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ichif1205.anime.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleRequest extends JsonArrayRequest {
    private Response.Listener<List<Article>> mListener;

    public ArticleRequest(String url, Response.Listener<List<Article>> listener, Response.ErrorListener errorListener) {
        super(url, null, errorListener);
        mListener = listener;
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
        if (mListener == null) {
            return;
        }
        mListener.onResponse(articleList);
    }

}
