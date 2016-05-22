package com.android.hackernews.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hackernews.R;
import com.android.hackernews.model.HackerNewsItemData;
import com.android.hackernews.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 21-05-2016.
 */
public class UserCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<HackerNewsItemData> mHackerNewsItemDataList;
    private LayoutInflater mLayoutInflater;

    public UserCommentAdapter(Context context, List<HackerNewsItemData> hackerNewsItemDataList) {
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
            convertView = mLayoutInflater.inflate(R.layout.user_comment_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mUserCommentTv = (TextView) convertView.findViewById(R.id.user_comment);
            viewHolder.mUserCommentAuthor = (TextView) convertView.findViewById(R.id.comment_author);
            viewHolder.mUserCommentTime = (TextView)convertView.findViewById(R.id.comment_time);
            viewHolder.mSubCommentAuthor = (TextView)convertView.findViewById(R.id.sub_comment);
            viewHolder.mSubCommentText = (TextView)convertView.findViewById(R.id.sub_comment_user);
            viewHolder.mSubCommentTime = (TextView)convertView.findViewById(R.id.sub_comment_time);
            viewHolder.subCommentSection = (LinearLayout)convertView.findViewById(R.id.sub_comment_section);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HackerNewsItemData data = getItem(position);
        viewHolder.mUserCommentTv.setText(Html.fromHtml(!TextUtils.isEmpty(data.getText()) ? data.getText() : "No comments"));
        viewHolder.mUserCommentAuthor.setText("By: "+data.getBy());
        viewHolder.mUserCommentTime.setText("Time: "+ Utility.getFormattedDateTime(data.getTime()));
        if(data.getSubCommentData() != null) {
            viewHolder.subCommentSection.setVisibility(View.VISIBLE);
            viewHolder.mSubCommentAuthor.setText("By: " + data.getSubCommentData().getBy());
            viewHolder.mSubCommentTime.setText("Time: " + Utility.getFormattedDateTime(data.getSubCommentData().getTime()));
            viewHolder.mSubCommentText.setText(Html.fromHtml(!TextUtils.isEmpty(data.getSubCommentData().getText()) ? data.getSubCommentData().getText() : "No comments"));
        } else {
            viewHolder.subCommentSection.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView mUserCommentTv;
        TextView mUserCommentAuthor;
        TextView mUserCommentTime;
        TextView mSubCommentAuthor;
        TextView mSubCommentTime;
        TextView mSubCommentText;
        LinearLayout subCommentSection;
    }
}
