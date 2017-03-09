package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.licrafter.cnode.model.entity.UserDetail;

/**
 * author: shell
 * date 2017/3/6 下午2:55
 **/
public class UserDetailModel {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private UserDetail data;

}
