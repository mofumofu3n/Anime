package com.ichif1205.anime.model;

import android.text.TextUtils;
import android.text.format.DateFormat;

import com.parse.ParseObject;

public class Article {
    private static final String OBJECT_ID = "objectId";
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String IMAGE = "image";
    private static final String FEED = "feed";
    private static final String PUBLISHED_AT = "publishedAt";
    public static final String CLICK = "click";

    private static final String FEED_TITLE = "title";

    private static final String DATE_FORMAT = "MM/dd kk:mm";

    public final String id;
    public final String title;
    public final String url;
    public final String image;
    //    public final String feedName;
    public final ParseObject feed;
    public final String date;
    public final int click;

    public final ParseObject object;

    public Article(ParseObject obj) {
        object = obj;

        id = obj.getString(OBJECT_ID);
        title = obj.getString(TITLE);
        url = obj.getString(URL);
        image = obj.getString(IMAGE);
        feed = obj.getParseObject(FEED);
        date = changeDateFormat(Long.valueOf(obj.getString(PUBLISHED_AT)));
        click = obj.getInt(CLICK);
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
