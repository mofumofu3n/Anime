package com.ichif1205.anime.request;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    private static final int KBYTE = 1024;
    private static final int MAX_CACHE_SIZE = 8;

    private static int getDefaultLruCacheSize() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / KBYTE);
        int cacheSize = maxMemory / MAX_CACHE_SIZE;       // 最大メモリに依存

        return cacheSize;
    }

    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    public LruBitmapCache(int sizeInKBytes) {
        super(sizeInKBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / KBYTE;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
