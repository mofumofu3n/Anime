package com.ichif1205.anime;

import com.ichif1205.anime.request.ParseConfig;
import com.parse.Parse;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, ParseConfig.APPLICATION_ID, ParseConfig.CLIENT_KEY);
    }
}
