package com.charlie.newsapp.screens.news;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

@NewsActivityScope
public class NewsActivityViewModelFactory implements ViewModelProvider.Factory {


    @Inject
    NewsActivityViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(NewsActivityViewModel.class)) {
            return (T) new NewsActivityViewModel();
        } else {
            throw new IllegalArgumentException("Can not find ViewModel class " + modelClass.getName());
        }
    }
}


