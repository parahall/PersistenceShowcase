package com.androidacademy.persistenceshowcase.Models;

import com.google.gson.annotations.SerializedName;

public class Film {

    @SerializedName("title")
    private String title;

    @SerializedName("opening_crawl")
    private String description;

    private String url;

    public Film(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
