package com.simpool.leedayeon.listdetail;

/**
 * Created by yeony_lee on 2016. 9. 21..
 */

public class NewQuiz {
    private String title;
    private String description;
    private long end_time;
    private int is_obj; //객관식(obj)이면 1 주관식(subj)이면 0
    private String obj_1; //객관식 1번답지
    private String obj_2; //객관식 2번답지
    private String subj; //주관식 답지
    private String owner;
    private String right_answer;
    private String num; //몇명이 답을 찍었을까요

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    private String profile; //프로필 url

    public NewQuiz() {

    }

    public NewQuiz(String profile, String title, String description, long end_time, int is_obj, String obj_1, String obj_2, String owner, String right_answer) {
        this.profile = profile;
        this.title = title;
        this.obj_2 = obj_2;
        this.obj_1 = obj_1;
        this.is_obj = is_obj;
        this.end_time = end_time;
        this.description = description;
        this.owner = owner;
        this.right_answer = right_answer;
    }

    public NewQuiz(String profile, String title, String description, long end_time, int is_obj, String subj, String owner, String right_answer) {
        this.profile = profile;
        this.title = title;
        this.subj = subj;
        this.is_obj = is_obj;
        this.end_time = end_time;
        this.description = description;
        this.owner = owner;
        this.right_answer = right_answer;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getIs_obj() {
        return is_obj;
    }

    public void setIs_obj(int is_obj) {
        this.is_obj = is_obj;
    }

    public String getObj_1() {
        return obj_1;
    }

    public void setObj_1(String obj_1) {
        this.obj_1 = obj_1;
    }

    public String getObj_2() {
        return obj_2;
    }

    public void setObj_2(String obj_2) {
        this.obj_2 = obj_2;
    }

    public String getSubj() {
        return subj;
    }

    public void setSubj(String subj) {
        this.subj = subj;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
