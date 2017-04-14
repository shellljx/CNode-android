package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lijx on 2017/4/14.
 */

public class ReplyResultModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("reply_id")
    @Expose
    private String reply_id;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }
}
