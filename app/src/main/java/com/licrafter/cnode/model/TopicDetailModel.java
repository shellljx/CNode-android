package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.licrafter.cnode.model.entity.TopicDetail;

/**
 * author: shell
 * date 2017/3/1 上午10:51
 **/
public class TopicDetailModel {

    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("data")
    private TopicDetail data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public TopicDetail getData() {
        return data;
    }

    public void setData(TopicDetail data) {
        this.data = data;
    }
}
