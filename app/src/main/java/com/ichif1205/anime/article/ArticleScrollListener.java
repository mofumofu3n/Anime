package com.ichif1205.anime.article;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ArticleScrollListener extends RecyclerView.OnScrollListener {
    private static final int PAGING_POSITION = 3;

    private final LinearLayoutManager mLayoutManager;
    private OnPositionListener mListener;
    private int mTotalItemCount;

    public ArticleScrollListener(LinearLayoutManager manager) {
        mLayoutManager = manager;
    }

    public void setOnPositionListener(OnPositionListener listener) {
        mListener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (mListener == null) {
            return;
        }

        final int visibleItemCount = mLayoutManager.getChildCount();
        final int totalItemCount = mLayoutManager.getItemCount();
        final int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
        final int firstFullyVisiblePosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();

        if (firstFullyVisiblePosition == 0) {
            mListener.onTopItemPosition(true);
        } else {
            mListener.onTopItemPosition(false);
        }

        final int deltaPosition = totalItemCount - (firstVisibleItemPosition + visibleItemCount);
        if (deltaPosition == PAGING_POSITION && mTotalItemCount != totalItemCount) {
            mListener.onPagingItemPosition(totalItemCount);
            mTotalItemCount = totalItemCount;
        }
    }

    interface OnPositionListener {
        /**
         * 一番目のアイテム
         *
         * @param isTopPosition 一番目が完全に表示されている場合、<em>true</em>
         */
        void onTopItemPosition(boolean isTopPosition);

        /**
         * ページングの位置
         *
         * @param totalItemCount 現在のアイテム数
         */
        void onPagingItemPosition(int totalItemCount);

        /**
         * 現在の位置
         *
         * @param currentItemPosition 現在のスクロール位置
         */
        void onCurrentPosition(int currentItemPosition);
    }
}
