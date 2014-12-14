package com.ichif1205.anime.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ichif1205.anime.R;
import com.ichif1205.anime.model.Article;
import com.ichif1205.anime.request.ArticleRequest;
import com.ichif1205.anime.request.RequestManager;

import java.util.List;

public class ArticleFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArticleRequest request = new ArticleRequest(
                "http://192.168.33.10/anime/article/read",
                createOnListener(),
                createErrorListener());

        getRequestQueue(getActivity()).add(request);
    }

    private RequestQueue getRequestQueue(Context context) {
        return RequestManager.getInstance().getRequestQueue(context);
    }

    private Response.Listener<List<Article>> createOnListener() {
        return new Response.Listener<List<Article>>() {
            @Override
            public void onResponse(List<Article> response) {

            }
        };
    }

    private Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }

}
