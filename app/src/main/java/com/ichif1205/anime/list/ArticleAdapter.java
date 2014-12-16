package com.ichif1205.anime.list;

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

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private final ImageLoader mImageLoader;
    private ArrayList<Article> mList;
    private OnItemClickListener mListener;

    public ArticleAdapter(ImageLoader loader) {
        mImageLoader = loader;
        mList = new ArrayList<>();
    }

    public void add(List<Article> list) {
        mList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mList == null || mList.size() == 0) {
            return;
        }
        final Article article = mList.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        }
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.title)
        TextView mTitleView;
        @InjectView(R.id.image)
        NetworkImageView mImageView;

        private Article mArticle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(this);
        }

        public void bind(Article article) {
            mTitleView.setText(article.title);
            if (TextUtils.isEmpty(article.url)) {
                mImageView.setVisibility(View.GONE);
            } else {
                mImageView.setImageUrl(article.image, mImageLoader);
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
}
