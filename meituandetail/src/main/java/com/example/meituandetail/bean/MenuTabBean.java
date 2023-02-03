package com.example.meituandetail.bean;

public class MenuTabBean {
    private String name;

    public MenuTabBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MenuTabBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
