package com.example.minitiktok.samecity_page;

public class TalkData {
    String time, name, content;
    int imgID;
    public TalkData(String time, String name, String content, int imgID){
        this.name = name;
        this.time = time;
        this.content = content;
        this.imgID = imgID;
    }
}
