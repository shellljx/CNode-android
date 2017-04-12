package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lijx on 2017/4/12.
 */

public class MarkResultModel {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("marked_msgs")
    @Expose
    private MarkedMsg[] marked_msgs;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public MarkedMsg[] getMarked_msgs() {
        return marked_msgs;
    }

    public void setMarked_msgs(MarkedMsg[] marked_msgs) {
        this.marked_msgs = marked_msgs;
    }

    public class MarkedMsg {
        @SerializedName("id")
        @Expose
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
