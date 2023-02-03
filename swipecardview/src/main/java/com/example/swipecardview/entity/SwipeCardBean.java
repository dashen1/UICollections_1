package com.example.swipecardview.entity;

import java.util.ArrayList;
import java.util.List;

public class SwipeCardBean {

    private int position;
    private String url;
    private String name;

    public SwipeCardBean(int position, String url, String name) {
        this.position = position;
        this.url = url;
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<SwipeCardBean> initData(){
        List<SwipeCardBean> data = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            data.add(new SwipeCardBean(i,"https://"+i+".com","图片 "+i));
        }
        return data;
    }
}

