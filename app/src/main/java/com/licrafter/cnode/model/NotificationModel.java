package com.licrafter.cnode.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.licrafter.cnode.model.entity.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * author: shell
 * date 2017/3/16 下午5:28
 **/
public class NotificationModel {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("has_read_messages")
        @Expose
        private List<Notification> has_read_messages = new ArrayList<>();
        @SerializedName("hasnot_read_messages")
        @Expose
        private List<Notification> hasnot_read_messages = new ArrayList<>();

        public List<Notification> getHas_read_messages() {
            return has_read_messages;
        }

        public void setHas_read_messages(List<Notification> has_read_messages) {
            this.has_read_messages = has_read_messages;
        }

        public List<Notification> getHasnot_read_messages() {
            return hasnot_read_messages;
        }

        public void setHasnot_read_messages(List<Notification> hasnot_read_messages) {
            this.hasnot_read_messages = hasnot_read_messages;
        }
    }
}
