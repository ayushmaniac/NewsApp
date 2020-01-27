package com.charlie.newsapp.screens.home.categoryadapter;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.charlie.newsapp.BR;


public class CategoryModel extends BaseObservable {

    private int image;
    private String categoryName;
    private boolean indicator;
    private String id;

    public CategoryModel(int image, String categoryName, boolean indicator, String id) {
        this.image = image;
        this.categoryName = categoryName;
        this.indicator = indicator;
        this.id = id;
        notifyChange();
    }

    @Bindable
    public int getImage() {
        return image;
    }

    @Bindable
    public String getCategoryName() {
        return categoryName;
    }

    @Bindable
    public boolean isIndicator() {
        return indicator;
    }

    public void setImage(int image) {
        this.image = image;
        notifyPropertyChanged(com.charlie.newsapp.BR.image);
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyPropertyChanged(com.charlie.newsapp.BR.categoryName);

    }

    public void setIndicator(boolean indicator) {
        this.indicator = indicator;
        notifyPropertyChanged(com.charlie.newsapp.BR.indicator);

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
