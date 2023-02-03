package com.example.meituandetail.bean;

public class MenuChildBean {

    private String groupName;
    private String childName;

    public MenuChildBean(String groupName, String childName) {
        this.groupName = groupName;
        this.childName = childName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    @Override
    public String toString() {
        return "MenuChildBean{" +
                "groupName='" + groupName + '\'' +
                ", childName='" + childName + '\'' +
                '}';
    }
}
