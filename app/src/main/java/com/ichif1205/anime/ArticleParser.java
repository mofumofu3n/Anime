package com.ichif1205.anime;

import com.ichif1205.anime.model.Article;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleParser {
    private final List<ParseObject> mParseObjects;

    public ArticleParser(List<ParseObject> objects) {
        mParseObjects = objects;
    }

    public List<Article> parse() {
        final List<Article> articleList = new ArrayList<>();

        for (ParseObject object : mParseObjects) {
            articleList.add(new Article(object));
        }

        return articleList;
    }
}
