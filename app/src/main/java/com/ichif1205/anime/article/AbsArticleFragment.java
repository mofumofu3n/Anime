package com.ichif1205.anime.article;

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
import com.ichif1205.anime.request.AbsArticleRequest;
import com.ichif1205.anime.request.RequestManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class AbsArticleFragment extends Fragment {
    @InjectView(R.id.swipe_refresh)
    public SwipeRefreshLayout mRefreshLayout;

    @InjectView(R.id.article_list)
    public RecyclerView mRecyclerView;

    @InjectView(R.id.loading)
    public ProgressBar mLoading;

    protected ArticleAdapter mAdapter;

    public AbsArticleFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        final AbsArticleRequest request = createArticleRequest();
        request.find();
        showLoading();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusHolder.get().register(this);
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(createOnItemClickListener());
        }
    }

    @Override
    public void onPause() {
        BusHolder.get().unregister(this);
        super.onPause();

        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(null);
        }
    }

    /**
     * Pull to Refresh の処理
     *
     * @return Pull to Refreshのリスナー
     */
    private SwipeRefreshLayout.OnRefreshListener createRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final AbsArticleRequest request = createArticleRequest();
                request.forceFind();
            }
        };
    }

    /**
     * 読み込み中の表示
     */
    protected void showLoading() {
        mRecyclerView.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
    }

    /**
     * 記事を表示
     */
    protected void showSuccess() {
        mLoading.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    /**
     * Parseへのリクエストを生成する
     *
     * @return {@link com.ichif1205.anime.request.AbsArticleRequest} リクエストクラスのインスタンス
     */
    protected abstract AbsArticleRequest createArticleRequest();

    protected abstract ArticleAdapter.OnItemClickListener createOnItemClickListener();

    /**
     * スクロール時の処理
     *
     * @return　スクロールのイベントリスナー
     */
    protected ArticleScrollListener.OnPositionListener createPositionListener() {
        return new ArticleScrollListener.OnPositionListener() {
            @Override
            public void onTopItemPosition(boolean isTopPosition) {
                // 画面最上部にいる時のみ、Pull to Refreshを有効にする
                mRefreshLayout.setEnabled(isTopPosition);
            }

            @Override
            public void onPagingItemPosition(int totalItemCount) {
                final AbsArticleRequest request = createArticleRequest();
                request.findPaging(totalItemCount);
            }

            @Override
            public void onCurrentPosition(int currentItemPosition) {

            }
        };
    }

}

