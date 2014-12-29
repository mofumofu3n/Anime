package com.ichif1205.anime.twitter;

import android.os.Handler;
import android.util.Log;

import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.model.Twitter;


import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class StreamReceivedListener implements StatusListener {
    @Override
    public void onStatus(Status status) {
        Log.d("hoge", String.format("name: %s, text: %s", status.getUser().getScreenName(), status.getText()));
        final Twitter twitter = new Twitter(status);
        BusHolder.get().post(new ReceivedStream(twitter));

    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {

    }

    @Override
    public void onStallWarning(StallWarning warning) {

    }

    @Override
    public void onException(Exception ex) {

    }

    public class ReceivedStream {
        private final Twitter mTwitter;

        public ReceivedStream(Twitter twitter) {
            mTwitter = twitter;
        }

        public Twitter getTwitter() {
            return mTwitter;
        }
    }
}
