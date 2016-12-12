package com.simpool.leedayeon.listdetail;

/**
 * Created by jiyoung on 2016-11-26.
 */


import java.io.Serializable;

/**
 * Created by jiyoung on 2016-11-22.
 */

public class User implements Serializable {
    private String name;
    private String email;
    private String profile;

    public User(){}

    public User(String name, String email, String profile){
        this.name = name;
        this.email = email;
        this.profile = profile;

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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}

