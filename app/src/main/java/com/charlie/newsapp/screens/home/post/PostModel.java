package com.charlie.newsapp.screens.home.post;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class PostModel extends BaseObservable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    private String image;
    private String title;
    private String source;
    private String time;
    private String publishedAt;
    private String author;
    private String url;


    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Bindable
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Bindable
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    @Bindable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
