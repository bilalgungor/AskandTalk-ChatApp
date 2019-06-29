package com.example.galatasaray.askandtalkv2.model;

public class Users {
    public  String fullname, profileimage,biography, id;

    public Users() {

    }

    public Users(String fullname, String profileimage, String biography, String id) {
        this.fullname = fullname;
        this.profileimage = profileimage;
        this.biography = biography;
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
