package com.android.hackernews.api;

import android.content.Context;

import com.android.hackernews.utils.Utility;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by hp pc on 21-05-2016.
 */
public class WebService {

    private static String ENDPOINT = "https://hacker-news.firebaseio.com/v0/";
    private static APIInterface sApiInterface;
    private static Context mContext;

    private WebService() {}

    public static APIInterface getApiServiceMethods(Context context) {
        if (sApiInterface == null) {
            mContext = context;
            sApiInterface = getServicesMethods();
        }
        return sApiInterface;
    }

    private static APIInterface getServicesMethods() {

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(5, TimeUnit.MINUTES);
        mOkHttpClient.setReadTimeout(5, TimeUnit.MINUTES);

        if (getCache() != null)
            mOkHttpClient.setCache(getCache());
        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(ENDPOINT).
                setClient(new OkClient(mOkHttpClient)).
                setRequestInterceptor(new CacheRequestInterceptor())
                .build();

        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        sApiInterface = restAdapter.create(APIInterface.class);
        return sApiInterface;
    }

    private static Cache getCache() {
        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        return new Cache(httpCacheDirectory, 10 * 1024 * 1024);
    }

    private static class CacheRequestInterceptor implements RequestInterceptor {

        @Override
        public void intercept(RequestFacade request) {
            if (Utility.checkInternetConnection(mContext)) {
                int maxAge = 60;
                request.addHeader("Cache-Control", "public, max-age=" + maxAge);
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                request.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
        }
    }
}
