package com.licrafter.cnode.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: shell
 * date 2017/3/1 上午10:57
 **/
public class Reply {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("author")
    private Author author;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("create_at")
    private Date create_at;

    @Expose
    @SerializedName("reply_id")
    private String reply_id;

    @Expose
    @SerializedName("ups")
    private List<String> ups = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public List<String> getUps() {
        return ups;
    }

    public void setUps(List<String> ups) {
        this.ups = ups;
    }
}
