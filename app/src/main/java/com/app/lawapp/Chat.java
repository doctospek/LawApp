package com.app.lawapp;/* Created By Ashwini Saraf on 2/18/2021*/

public class Chat {

    private String msg,from_id,to_id,created_at;
    private int id;

    public Chat() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "msg='" + msg + '\'' +
                ", from_id='" + from_id + '\'' +
                ", to_id='" + to_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id=" + id +
                '}';
    }
}
