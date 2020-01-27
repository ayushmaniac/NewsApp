package com.charlie.newsapp.screens.news;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class NewsActivityViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private NewsActivityModel activityModel = new NewsActivityModel();
    ;

    public NewsActivityViewModel() {
    }

    public NewsActivityModel getActivityModel() {
        return activityModel;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
