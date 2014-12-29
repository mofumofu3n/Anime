package com.ichif1205.anime.model;

import twitter4j.Status;

public class Twitter {
    public final String screenName;
    public final String text;
    public final String date;


    public Twitter(Status status) {
        screenName = status.getUser().getScreenName();
        text = status.getText();
        date = status.getCreatedAt().toString();
    }
}
