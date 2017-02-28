package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author: shell
 * date 2017/2/24 下午3:55
 **/
public class Author {

    @SerializedName("loginname")
    @Expose
    private String loginname;
    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
