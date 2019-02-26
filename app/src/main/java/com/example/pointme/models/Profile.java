package com.example.pointme.models;

public class Profile {

    private String email;
    private String name;
    private String tel;
    private String ig;
    private String image;

    public Profile(){}

    public Profile(String name, String email, String tel, String image, String ig){
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.image = image;
        this.ig = ig;
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
}
