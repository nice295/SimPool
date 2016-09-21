package com.example.leedayeon.listdetail;

/**
 * Created by yeony_lee on 2016. 9. 21..
 */

public class NewQuiz {
    private String title;
    private String description;
    private String end_time;

    public NewQuiz() {

    }

    public NewQuiz(String title, String description, String end_time) {
        this.description = description;
        this.title = title;
        this.end_time = end_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
