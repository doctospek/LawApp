package com.app.lawapp;/* Created By Ashwini Saraf on 2/18/2021*/

import java.io.Serializable;

public class Notification implements Serializable {

    int id, is_accepted;
    String user_name, lawyer_id, user_id, created_at, image_name, desc;

    public Notification() {
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(int is_accepted) {
        this.is_accepted = is_accepted;
    }

    public String getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(String lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", is_accepted=" + is_accepted +
                ", user_name='" + user_name + '\'' +
                ", lawyer_id='" + lawyer_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", image_name='" + image_name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
