package com.ichif1205.anime.model;

import android.text.TextUtils;
import android.text.format.DateFormat;

import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {
    private static final String ID = "id";
    private static final String OBJECT_ID = "objectId";
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String IMAGE = "image";
    private static final String FEED = "feed";
    private static final String DATE = "published_date";
    private static final String PUBLISHED_AT = "publishedAt";
    private static final String CLICK = "click";

    private static final String FEED_TITLE = "title";

    private static final String DATE_FORMAT = "MM/dd kk:mm";

    public String id;
    public String title;
    public String url;
    public String image;
    public String feedName;
    public ParseObject feed;
    public String date;
    public int click;

    public static Article parse(ParseObject obj) {
        final Article article = new Article();
        article.id = obj.getString(OBJECT_ID);
        article.title = obj.getString(TITLE);
        article.url = obj.getString(URL);
        article.image = obj.getString(IMAGE);
        article.feed = obj.getParseObject(FEED);
        article.date = article.changeDateFormat(Long.valueOf(obj.getString(PUBLISHED_AT)));

        return article;
    }

    public static Article parse(JSONObject obj) {
        Article article = null;

        try {
            article = new Article();
            article.id = obj.getString(ID);
            article.title = obj.getString(TITLE);
            article.url = obj.getString(URL);
            article.image = obj.getString(IMAGE);
            article.feedName = obj.getString(FEED);
            article.date = article.changeDateFormat(obj.getLong(DATE));
            article.click = article.changeNullToZero(obj.getString(CLICK));
        } catch (JSONException e) {
        }

        return article;
    }

    /**
     * UnixTimeを文字列に変更
     *
     * @param timestamp millisec
     * @return MM/dd kk:mmにフォーマットされた日付
     */
    private String changeDateFormat(long timestamp) {
        final String dateFormat = (String) DateFormat.format(DATE_FORMAT, timestamp);
        return dateFormat;
    }

    private int changeNullToZero(String val) {
        if (!TextUtils.equals("null", val)) {
            return Integer.parseInt(val);
        }
        return 0;
    }
}
