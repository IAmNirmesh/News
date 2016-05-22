package com.android.hackernews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hackernews.R;
import com.android.hackernews.model.HackerNewsItemData;
import com.android.hackernews.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 21-05-2016.
 */
public class TopStoriesAdapter extends BaseAdapter {

    private Context mContext;
    private List<HackerNewsItemData> mHackerNewsItemDataList;
    private LayoutInflater mLayoutInflater;
    public TopStoriesAdapter(Context context, List<HackerNewsItemData> hackerNewsItemDataList) {
        mContext = context;
        mHackerNewsItemDataList = new ArrayList<>(hackerNewsItemDataList);
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mHackerNewsItemDataList.size();
    }

    @Override
    public HackerNewsItemData getItem(int i) {
        return mHackerNewsItemDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.top_stories_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.mTitleTv = (TextView) convertView.findViewById(R.id.top_stories_heading_text);
            viewHolder.mAuthorName = (TextView) convertView.findViewById(R.id.top_stories_author);
            viewHolder.mTime = (TextView)convertView.findViewById(R.id.top_stories_time);
            viewHolder.mWebLink = (TextView)convertView.findViewById(R.id.top_stories_web_link);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HackerNewsItemData data = getItem(position);
        viewHolder.mTitleTv.setText(data.getTitle());
        viewHolder.mAuthorName.setText("By: "+data.getBy());
        viewHolder.mTime.setText("Time: "+ Utility.getFormattedDateTime(data.getTime()));
        viewHolder.mWebLink.setText(data.getUrl());

        return convertView;
    }

    public void addData(List<HackerNewsItemData> mHackerNewsItemDataList) {
        this.mHackerNewsItemDataList = new ArrayList<>(mHackerNewsItemDataList);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView mTitleTv;
        TextView mAuthorName;
        TextView mTime;
        TextView mWebLink;
    }
}
