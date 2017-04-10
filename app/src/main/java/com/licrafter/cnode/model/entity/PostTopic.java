package com.licrafter.cnode.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lijx on 2017/4/10.
 */

public class PostTopic {

    @SerializedName("accesstoken")
    @Expose
    private String accesstoken;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("tab")
    @Expose
    private String tab;
    @SerializedName("content")
    @Expose
    private String content;

    public PostTopic(String accesstoken, String title, String tab, String content) {
        this.accesstoken = accesstoken;
        this.title = title;
        this.tab = tab;
        this.content = content;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
