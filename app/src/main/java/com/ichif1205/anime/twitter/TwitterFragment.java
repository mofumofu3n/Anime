package com.ichif1205.anime.twitter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterFragment extends Fragment {
    private TwitterStream mStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mStream = new TwitterStreamFactory().getInstance(setupOauth());
        mStream.addListener(createStatusListener());

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private OAuthAuthorization setupOauth() {
        final Configuration configuration = new ConfigurationBuilder().build();
        final OAuthAuthorization oauth = new OAuthAuthorization(configuration);
        oauth.setOAuthConsumer(Consts.CONSUMER_KEY, Consts.CONSUMER_SECRET);
        oauth.setOAuthAccessToken(new AccessToken(Consts.ACCESS_TOKEN, Consts.ACCESS_TOKEN_SECRET));
        return oauth;
    }

    @Override
    public void onResume() {
        super.onResume();
        mStream.filter(createQuery("#amaburi"));
    }

    private FilterQuery createQuery(String str) {
        final FilterQuery query = new FilterQuery();
        query.track(new String[]{str});

        return query;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mStream.removeListener(createStatusListener());
        // TODO shutdownをバックグラウンドスレッドで操作しないとStrictModeに引っかかる
        mStream.shutdown();
    }

    private StatusListener createStatusListener() {
        return new StatusListener() {
            @Override
            public void onStatus(Status status) {
                Log.d("hoge", String.format("name: %s, text: %s", status.getUser().getScreenName(), status.getText()));
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
        };
    }
}
