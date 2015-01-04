package com.ichif1205.anime.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Lineup {
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String TIME = "time;";
    private static final String STATION = "station";
    private static final String STATE = "state";
    private static final String NEXT = "next";
    private static final String EPISODE = "episode";
    private static final String TODAY = "today";
    private static final String WEEK = "week";

    public final String title;
    public final String url;
    public final String time;
    public final String station;
    public final String state;
    public final String next;
    public final String episode;
    public final String today;
    public final String week;

    public Lineup(JSONObject obj) throws JSONException {
        if (obj == null) {
            throw new JSONException("");
        }

        title = obj.getString(TITLE);
        url = obj.getString(URL);
        time = obj.getString(TIME);
        station = obj.getString(STATION);
        state = obj.getString(STATE);
        next = obj.getString(NEXT);
        episode = obj.getString(EPISODE);
        today = obj.getString(TODAY);
        week = obj.getString(WEEK);
    }
}
