package com.ichif1205.anime.request;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

public class ShowRequest extends AsyncTask<Void, Void, Void> {
    private static final String URL = "http://192.168.33.10/anime/shows/show";
    private final RequestBody mBody;

    private ShowRequest(Builder builder) {
        mBody = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                createParameter(builder)
        );
    }

    @Override
    protected Void doInBackground(Void... params) {
        final Request request = new Request.Builder()
                .url(URL)
                .post(mBody)
                .build();

        final OkHttpClient client = new OkHttpClient();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            // レスポンスはないので特に何もしない
        }
        return null;
    }

    private String createParameter(Builder builder) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("url=");
        stringBuilder.append(builder.mUrl);
        stringBuilder.append("&cookie=");
        stringBuilder.append(builder.mUserId);

        return stringBuilder.toString();
    }

    public static class Builder {
        public String mUserId;
        public String mUrl;

        public Builder() {

        }

        public Builder setUserId(String userId) {
            mUserId = userId;
            return this;
        }

        public Builder setUrl(String url) {
            mUrl = url;
            return this;
        }

        public ShowRequest build() {
            return new ShowRequest(this);
        }
    }
}
