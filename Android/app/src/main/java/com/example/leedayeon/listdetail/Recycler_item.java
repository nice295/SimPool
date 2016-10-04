package com.example.leedayeon.listdetail;

/**
 * Created by LG_note1 on 2016-08-22.
 */
public class Recycler_item {
    int image;
    String title;
    String desc;
    String date;
    int situation;

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }
    String getDate(){return this.date;}
    int getSituation(){return this.situation;}
    String getDesc(){
        return this.desc;
    }
    Recycler_item(int image, String title, String desc, String date, int situation){
        this.image=image;
        this.title=title;
        this.desc=desc;
        this.date=date;
        this.situation=situation;
    }
}
