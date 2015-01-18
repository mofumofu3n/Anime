package com.ichif1205.anime.article;

import com.ichif1205.anime.browser.BrowserActivity;
import com.ichif1205.anime.model.Article;
import com.ichif1205.anime.request.AbsArticleRequest;
import com.ichif1205.anime.request.ParseArticleRequest;
import com.squareup.otto.Subscribe;

import java.util.List;


public class NewArticleFragment extends AbsArticleFragment {

    @Override
    protected AbsArticleRequest createArticleRequest() {
        return new ParseArticleRequest();
    }

    @Override
    protected ArticleAdapter.OnItemClickListener createOnItemClickListener() {
        return new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article article) {
                if (article == null) {
                    return;
                }

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
}
