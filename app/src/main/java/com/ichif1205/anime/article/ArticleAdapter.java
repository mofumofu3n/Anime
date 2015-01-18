package com.ichif1205.anime.article;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ichif1205.anime.R;
import com.ichif1205.anime.model.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CARD = 0;
    private static final int TYPE_NO_IMAGE = 1;

    private final ImageLoader mImageLoader;
    private ArrayList<Article> mList;

    private OnItemClickListener mListener;

    public ArticleAdapter(ImageLoader loader) {
        mImageLoader = loader;
        mList = new ArrayList<>();
    }

    public void addFirst(List<Article> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void add(List<Article> list) {
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_CARD) {
            final View view = inflater.inflate(R.layout.layout_article, parent, false);
            return new CardHolder(view);
        } else if (viewType == TYPE_NO_IMAGE) {
            final View view = inflater.inflate(R.layout.layout_no_image_article, parent, false);
            return new NoImageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mList == null || mList.size() == 0) {
            return;
        }
        final Article article = mList.get(position);

        final int type = getItemViewType(position);
//        ((CardHolder) holder).bind(article);

        if (type == TYPE_CARD) {
            ((CardHolder) holder).bind(article);
        } else if (type == TYPE_NO_IMAGE) {
            ((NoImageHolder) holder).bind(article);
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        final Article article = mList.get(position);

        if (TextUtils.isEmpty(article.image)) {
            return TYPE_NO_IMAGE;
        } else {
            return TYPE_CARD;
        }
    }

    interface OnItemClickListener {
        void onItemClick(Article article);
    }

    public class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.title)
        TextView mTitleView;
        @InjectView(R.id.image)
        NetworkImageView mImageView;

        private Article mArticle;

        public CardHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(this);
        }

        public void bind(Article article) {
            mArticle = article;

            mTitleView.setText(mArticle.title);
            if (TextUtils.isEmpty(mArticle.image)) {
                mImageView.setVisibility(View.GONE);
            } else {
                mImageView.setImageUrl(mArticle.image, mImageLoader);
            }
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) {
                return;
            }

            mListener.onItemClick(mArticle);
        }
    }

    public class NoImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.title)
        TextView mTitleView;

        private Article mArticle;

        public NoImageHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Article article) {
            mArticle = article;
            mTitleView.setText(mArticle.title);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) {
                return;
            }

            mListener.onItemClick(mArticle);
        }
    }
}
