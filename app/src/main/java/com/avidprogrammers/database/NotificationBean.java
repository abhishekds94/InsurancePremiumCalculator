package com.avidprogrammers.database;

public class NotificationBean {
    String title,url,time;

    public String getTitle() {
        return title;
    }

    public NotificationBean() {
    }

    public void setTitle(String title) {
        this.title = title;

    }

    @Override
    public String toString() {
        return "NotificationBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NotificationBean(String title, String url, String time) {

        this.title = title;
        this.url = url;
        this.time = time;
    }
}
