package com.android.hackernews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp pc on 21-05-2016.
 */
public class HackerNewsItemData {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("by")
    @Expose
    private String by;
    @SerializedName("descendants")
    @Expose
    private Integer descendants;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kids")
    @Expose
    private List<Integer> kids = new ArrayList<Integer>();
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;

    private HackerNewsItemData subCommentData;

    /**
     *
     * @return
     * The by
     */
    public String getBy() {
        return by;
    }

    /**
     *
     * @param by
     * The by
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     *
     * @return
     * The descendants
     */
    public Integer getDescendants() {
        return descendants;
    }

    /**
     *
     * @param descendants
     * The descendants
     */
    public void setDescendants(Integer descendants) {
        this.descendants = descendants;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The kids
     */
    public List<Integer> getKids() {
        return kids;
    }

    /**
     *
     * @param kids
     * The kids
     */
    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    /**
     *
     * @return
     * The score
     */
    public Integer getScore() {
        return score;
    }

    /**
     *
     * @param score
     * The score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     *
     * @return
     * The time
     */
    public Integer getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public HackerNewsItemData getSubCommentData() {
        return subCommentData;
    }

    public void setSubCommentData(HackerNewsItemData subCommentData) {
        this.subCommentData = subCommentData;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
