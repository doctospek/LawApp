package com.app.lawapp.firebase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationProxy {


    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("postUrl")
    private String postUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
}
