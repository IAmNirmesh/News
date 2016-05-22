package com.android.hackernews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.hackernews.adapter.UserCommentAdapter;
import com.android.hackernews.api.WebService;
import com.android.hackernews.model.HackerNewsItemData;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends BaseActivity {

    public static final String KEY_COMMENT_LIST = "KEY_COMMENT_LIST";
    public static final String KEY_COMMENT_BUNDLE = "KEY_COMMENT_BUNDLE";
    private static String TAG = "CommentsActivity";
    private List<Integer> mCommentsIds;
    private ListView mUserCommentListView;
    private List<HackerNewsItemData> mHackerNewsItemDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCommentsIds = getIntent().getBundleExtra(KEY_COMMENT_BUNDLE).getIntegerArrayList(KEY_COMMENT_LIST);
        mUserCommentListView = (ListView) findViewById(R.id.user_comment_list_view);
        if (checkInternetConnection())
            getCommentsData();
        else
            showNoInternetConnectionToast();
    }

    public void getCommentsData() {
        if (mCommentsIds != null && mCommentsIds.size() != 0) {
            showProgress();
            new loadComments().execute();
        }
    }

    private class loadComments extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i < mCommentsIds.size() ; i++) {
                HackerNewsItemData hackerNewsItemData = WebService.getApiServiceMethods(CommentsActivity.this).getHackerNewsItemData(mCommentsIds.get(i) + "");
                if(hackerNewsItemData.getKids() != null && !hackerNewsItemData.getKids().isEmpty()) {
                    HackerNewsItemData data = WebService.getApiServiceMethods(CommentsActivity.this).getHackerNewsItemData(hackerNewsItemData.getKids().get(0) + "");
                    hackerNewsItemData.setSubCommentData(data);
                }
                mHackerNewsItemDataList.add(hackerNewsItemData);
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgress();
            if(!TextUtils.isEmpty(s)){
                UserCommentAdapter mAdapter = new UserCommentAdapter(CommentsActivity.this, mHackerNewsItemDataList);
                mUserCommentListView.setAdapter(mAdapter);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
