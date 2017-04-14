package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lijx on 2017/4/14.
 */

public class ReplyBody {

    @SerializedName("accesstoken")
    @Expose
    private String accesstoken;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("reply_id")
    @Expose
    private String reply_id;

    public ReplyBody(String accesstoken, String content, String reply_id) {
        this.accesstoken = accesstoken;
        this.content = content;
        this.reply_id = reply_id;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }
}
