package com.example.galatasaray.askandtalkv2.model;

public class Posts
{
    public String uid, time, date,profileimage,question, username, fullname,true_answer, false_answer,false_answer1,false_answer2;

    public Posts()
    {

    }

    public Posts(String uid, String time, String date, String profileimage,String question, String username,String fullname, String true_answer, String false_answer, String false_answer1, String false_answer2) {
        this.uid = uid;
        this.time = time;
        this.date = date;
        this.profileimage = profileimage;
        this.question = question;
        this.username = username;
        this.fullname= fullname;
        this.true_answer = true_answer;
        this.false_answer = false_answer;
        this.false_answer1 = false_answer1;
        this.false_answer2 = false_answer2;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTrue_answer() {
        return true_answer;
    }

    public void setTrue_answer(String true_answer) {
        this.true_answer = true_answer;
    }

    public String getFalse_answer() {
        return false_answer;
    }

    public void setFalse_answer(String false_answer) {
        this.false_answer = false_answer;
    }

    public String getFalse_answer1() {
        return false_answer1;
    }

    public void setFalse_answer1(String false_answer1) {
        this.false_answer1 = false_answer1;
    }

    public String getFalse_answer2() {
        return false_answer2;
    }

    public void setFalse_answer2(String false_answer2) {
        this.false_answer2 = false_answer2;
    }
}
