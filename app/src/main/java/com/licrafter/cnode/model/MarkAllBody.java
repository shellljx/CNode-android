package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lijx on 2017/4/12.
 */

public class MarkAllBody {
    @SerializedName("accesstoken")
    @Expose
    private String accesstoken;

    public MarkAllBody(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
