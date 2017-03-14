package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author: shell
 * date 2017/3/13 下午8:52
 **/
public class LoginResultModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("loginname")
    @Expose
    private String loginname;
    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;
    @SerializedName("id")
    @Expose
    private String id;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getAvatar_url() {
        return avatar_url.contains("http") | avatar_url.contains("https") ? avatar_url : "https:" + avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
