package com.ichif1205.anime.request;

public final class RequestManager {
    private static RequestManager sInstance;

    public static RequestManager getInstance() {
        if (sInstance == null) {
            sInstance = new RequestManager();
        }
        return sInstance;
    }

    private RequestManager() {
    }
}
