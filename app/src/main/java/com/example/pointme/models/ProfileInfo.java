package com.example.pointme.models;

import com.google.firebase.database.Exclude;

public class ProfileInfo {

    private String email;
    private String name;
    private String tel;
    private String ig;
    private String image;
    private String timeD;
    private String key;
    private String title;

    public ProfileInfo() {
    }

    public ProfileInfo(String name, String email, String tel, String image, String ig, String timeD) {
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.image = image;
        this.ig = ig;
        this.timeD = timeD;
        this.key = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIg() {
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeD() {
        return timeD;
    }

    public void setTimeD(String timeD) {
        this.timeD = timeD;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
