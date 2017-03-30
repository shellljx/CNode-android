package com.licrafter.cnode.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * author: shell
 * date 2017/3/16 下午5:29
 **/
public class Notification {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("has_read")
    @Expose
    private Boolean has_read;

    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("topic")
    @Expose
    private Topic topic;
    @SerializedName("reply")
    @Expose
    private Reply reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHas_read() {
        return has_read;
    }

    public void setHas_read(Boolean has_read) {
        this.has_read = has_read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }
}
