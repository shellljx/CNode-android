package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lijx on 2017/4/12.
 */

public class CollectionBody {

    @SerializedName("accesstoken")
    @Expose
    private String accesstoken;
    @SerializedName("topic_id")
    @Expose
    private String topic_id;

    public CollectionBody(String accesstoken, String topic_id) {
        this.accesstoken = accesstoken;
        this.topic_id = topic_id;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
}
