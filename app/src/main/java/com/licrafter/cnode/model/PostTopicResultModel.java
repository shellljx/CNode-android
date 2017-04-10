package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lijx on 2017/4/10.
 */

public class PostTopicResultModel {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("topic_id")
    @Expose
    private String topic_id;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
}
