package com.ichif1205.anime.twitter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ichif1205.anime.BusHolder;
import com.ichif1205.anime.R;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterFragment extends Fragment {
    @InjectView(R.id.twitter_list)
    public RecyclerView mRecyclerView;

    private TwitterStream mStream;
    private Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_twitter, container, false);
        ButterKnife.inject(this, root);

        mAdapter = new Adapter();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mStream = new TwitterStreamFactory().getInstance(setupOauth());
        mStream.addListener(new StreamReceivedListener());

        return root;
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
        BusHolder.get().register(this);
        mStream.filter(createQuery("#C87"));
    }

    private FilterQuery createQuery(String str) {
        final FilterQuery query = new FilterQuery();
        query.track(new String[]{str});

        return query;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mStream.removeListener(new StreamReceivedListener());
        // TODO shutdownをバックグラウンドスレッドで操作しないとStrictModeに引っかかる
        mStream.shutdown();
        BusHolder.get().unregister(this);
    }

    @Subscribe
    public void onReceivedStream(StreamReceivedListener.ReceivedStream stream) {
        mAdapter.add(stream.getTwitter());
        mAdapter.notifyDataSetChanged();
    }
}
