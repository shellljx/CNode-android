package com.licrafter.cnode.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * author: shell
 * date 2017/3/6 下午3:41
 **/
public class UserDetail {

    @SerializedName("loginname")
    @Expose
    private String loginname;
    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;
    @SerializedName("githubUsername")
    @Expose
    private String githubUsername;
    @SerializedName("create_at")
    @Expose
    private Date create_at;
    @SerializedName("score")
    @Expose
    private Integer score;

    @SerializedName("recent_topics")
    @Expose
    private List<Topic> recent_topics;

    public List<Topic> getRecent_replies() {
        return recent_replies;
    }

    public void setRecent_replies(List<Topic> recent_replies) {
        this.recent_replies = recent_replies;
    }

    @SerializedName("recent_replies")
    @Expose
    private List<Topic> recent_replies;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Topic> getRecent_topics() {
        return recent_topics;
    }

    public void setRecent_topics(List<Topic> recent_topics) {
        this.recent_topics = recent_topics;
    }
}
