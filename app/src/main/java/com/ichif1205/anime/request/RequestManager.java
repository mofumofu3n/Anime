package com.ichif1205.anime.request;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public final class RequestManager {
    private static RequestManager sInstance;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static RequestManager getInstance() {
        if (sInstance == null) {
            sInstance = new RequestManager();
        }
        return sInstance;
    }

    private RequestManager() {
    }

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(Context context) {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getRequestQueue(context), new LruBitmapCache());
        }
        return mImageLoader;
    }
}
