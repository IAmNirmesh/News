package com.android.hackernews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.hackernews.adapter.TopStoriesAdapter;
import com.android.hackernews.api.WebService;
import com.android.hackernews.listener.EndlessScrollListener;
import com.android.hackernews.model.HackerNewsItemData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MainActivity extends BaseActivity {

    private static String TAG = "MainActivity";
    private ListView mTopStoriesListView;
    private List<HackerNewsItemData> mHackerNewsItemDataList = new ArrayList<>();
    private JSONArray mJsonArray;
    private int startCount = 0;
    private int endCount = 10;
    private TopStoriesAdapter mAdapter;
    private SwipeRefreshLayout mSwipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTopStoriesListView = (ListView) findViewById(R.id.top_stories_list_view);
        mSwipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        mAdapter = new TopStoriesAdapter(MainActivity.this, mHackerNewsItemDataList);
        mTopStoriesListView.setAdapter(mAdapter);
        addScrollAndClickListeners();
        addSwipeToRefreshListener();
        getTopStories();
    }

    private void addSwipeToRefreshListener() {
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHackerNewsItemDataList.clear();
                getTopStories();
            }
        });
    }

    private void getTopStories() {
        if(checkInternetConnection()) {
            showProgress();
            WebService.getApiServiceMethods(this).getTopStories(new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    if (response != null) {
                        try {
                            String body = new String(((TypedByteArray) response.getBody()).getBytes());
                            mJsonArray = new JSONArray(body);
                            new loadTopStoriesData().execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    hideProgress();
                }
            });
        } else {
            showNoInternetConnectionToast();
        }
    }

    private class loadTopStoriesData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            if(mJsonArray != null && mJsonArray.length() != 0) {
                System.out.println("size of json array:" + mJsonArray.length());
                try {
                    for (int i = startCount; i < endCount; i++) {
                        Integer obj = (Integer) mJsonArray.get(i);
                        HackerNewsItemData hackerNewsItemData = WebService.getApiServiceMethods(MainActivity.this).getHackerNewsItemData(obj.toString());
                        if (hackerNewsItemData != null)
                            mHackerNewsItemDataList.add(hackerNewsItemData);
                    }
                    return "success";
                } catch (JSONException ex) {
                    Log.e(TAG, ex.toString());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            hideProgress();
            if(mSwipeContainer.isRefreshing())
                mSwipeContainer.setRefreshing(false);
            if(!TextUtils.isEmpty(result)) {
                mAdapter.addData(mHackerNewsItemDataList);
            }
        }
    }

    private void addScrollAndClickListeners() {
       mTopStoriesListView.setOnScrollListener(new EndlessScrollListener() {
           @Override
           public boolean onLoadMore(int page, int totalItemsCount) {
               startCount = startCount + 10;
               endCount = endCount + 10;
               showProgress();
               new loadTopStoriesData().execute();
               return true;
           }
       });

        mTopStoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                HackerNewsItemData hackerNewsItemData = (HackerNewsItemData) adapterView.getItemAtPosition(pos);
                Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList(CommentsActivity.KEY_COMMENT_LIST, new ArrayList<>(hackerNewsItemData.getKids()));
                intent.putExtra(CommentsActivity.KEY_COMMENT_BUNDLE, bundle);
                startActivity(intent);
            }
        });
    }
}
