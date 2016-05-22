package com.android.hackernews.api;

import com.android.hackernews.model.HackerNewsItemData;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by hp pc on 21-05-2016.
 */
public interface APIInterface {

    @GET("/topstories.json")
    void getTopStories(Callback<Response> response);
    @GET("/item/{id}.json")
    HackerNewsItemData getHackerNewsItemData(@Path("id") String id);
}
