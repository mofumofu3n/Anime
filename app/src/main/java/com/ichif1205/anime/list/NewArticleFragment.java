package com.ichif1205.anime.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.R;
import com.ichif1205.anime.browser.BrowserActivity;
import com.ichif1205.anime.model.Article;
import com.ichif1205.anime.request.ParseArticleRequest;
import com.ichif1205.anime.request.RequestManager;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewArticleFragment extends Fragment {
    @InjectView(R.id.swipe_refresh)
    public SwipeRefreshLayout mRefreshLayout;

    @InjectView(R.id.article_list)
    public RecyclerView mRecyclerView;

    @InjectView(R.id.loading)
    public ProgressBar mLoading;

    private ArticleAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ButterKnife.inject(this, view);

        mRefreshLayout.setOnRefreshListener(createRefreshListener());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ArticleAdapter(RequestManager.getInstance().getImageLoader(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        final ArticleScrollListener listener = new ArticleScrollListener(layoutManager);
        listener.setOnPositionListener(createPositionListener());
        mRecyclerView.setOnScrollListener(listener);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ParseArticleRequest request = new ParseArticleRequest();
        request.find();
        loading();
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

    private SwipeRefreshLayout.OnRefreshListener createRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final ParseArticleRequest request = new ParseArticleRequest();
                request.forceFind();
            }
        };
    }

    private void loading() {
        mRecyclerView.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
    }

    private void showSuccess() {
        mLoading.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private ArticleScrollListener.OnPositionListener createPositionListener() {
       return new ArticleScrollListener.OnPositionListener() {
           @Override
           public void onTopItemPosition(boolean isTopPosition) {
               mRefreshLayout.setEnabled(isTopPosition);
           }

           @Override
           public void onPagingItemPosition(int totalItemCount) {
               final ParseArticleRequest request = new ParseArticleRequest();
               request.findPaging(totalItemCount);
           }

           @Override
           public void onCurrentPosition(int currentItemPosition) {

           }
       };
    }

    @Subscribe
    public void onResponse(ParseArticleRequest.SuccessEvent event) {
        final boolean isPaging = event.isPaging();
        final List<Article> articleList = event.getList();
        if (isPaging) {
            mAdapter.add(articleList);
        } else {
            mAdapter.addFirst(articleList);
        }
        mAdapter.notifyDataSetChanged();
        showSuccess();
    }

    @Subscribe
    public void onError(ParseArticleRequest.ErrorEvent event) {

    }

    @Subscribe
    public void onItemClick(ArticleAdapter.OnItemClick event) {
        final Article article = event.getArticle();

        article.object.increment("click");
        article.object.saveInBackground();

//        final ShowRequest request = new ShowRequest
//                .Builder()
//                .setUrl(article.url)
//                .setUserId("111111")
//                .build();
//        request.execute();

        BrowserActivity.start(getActivity(), article);
    }
}
