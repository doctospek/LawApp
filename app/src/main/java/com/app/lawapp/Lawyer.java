package com.app.lawapp;/* Created By Ashwini Saraf on 3/6/2021*/

import java.io.Serializable;

public class Lawyer implements Serializable {

    String id, name, email, mobile, password, lawyer_type, created_at,firebase_token;

    public Lawyer() {
    }

    @Override
    public String toString() {
        return "Lawyer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", lawyer_type='" + lawyer_type + '\'' +
                ", created_at='" + created_at + '\'' +
                ", firebase_token='" + firebase_token + '\'' +
                '}';
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLawyer_type() {
        return lawyer_type;
    }

    public void setLawyer_type(String lawyer_type) {
        this.lawyer_type = lawyer_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

}
