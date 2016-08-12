package com.example.leedayeon.listdetail;

import java.io.Serializable;

/**
 * Created by LeeDaYeon on 2016-07-19.
 */
public class Post implements Serializable {

    private String title;
    private String description;

    public Post() {

    }

    public Post(String title, String description) {
        this.description = description;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
