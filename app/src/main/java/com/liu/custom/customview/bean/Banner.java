package com.liu.custom.customview.bean;

/**
 * Created by jiemi-server on 2017/5/17.
 */

public class Banner {

    private String title;
    private String url;

    public Banner(String url,String title) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
