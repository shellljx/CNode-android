package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * author: shell
 * date 2017/2/24 下午3:50
 **/
public class TabModel {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private List<Topic> data = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Topic> getData() {
        return data;
    }

    public void setData(List<Topic> data) {
        this.data = data;
    }
}
