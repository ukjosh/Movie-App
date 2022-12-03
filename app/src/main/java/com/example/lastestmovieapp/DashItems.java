package com.example.lastestmovieapp;

public class DashItems {

    String title;
    Integer searchId;
    Integer icon;
    Integer bgColor;

    public DashItems(String title, Integer searchId, Integer icon, Integer bgColor) {
        this.title = title;
        this.searchId = searchId;
        this.icon = icon;
        this.bgColor = bgColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSearchId() {
        return searchId;
    }

    public void setSearchId(Integer searchId) {
        this.searchId = searchId;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getBgColor() {
        return bgColor;
    }

    public void setBgColor(Integer bgColor) {
        this.bgColor = bgColor;
    }
}
