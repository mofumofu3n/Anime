package com.ichif1205.anime.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.R;
import com.ichif1205.anime.model.Article;
import com.ichif1205.anime.request.ArticleRequest;
import com.ichif1205.anime.request.RequestManager;
import com.squareup.otto.Subscribe;

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

        final ArticleRequest request = new ArticleRequest("http://192.168.33.10/anime/article/read");
        getRequestQueue(getActivity()).add(request);
    }

    private RequestQueue getRequestQueue(Context context) {
        return RequestManager.getInstance().getRequestQueue(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusHolder.get().register(this);
    }

    @Override
    public void onPause() {
        BusHolder.get().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onResponse(ArticleRequest.SuccessEvent event) {
        final List<Article> articleList = event.getList();

        for (Article article : articleList) {
            Log.d("hoge", article.title);
        }
    }

    @Subscribe
    public void onError(ArticleRequest.ErrorEvent event) {

    }
}
