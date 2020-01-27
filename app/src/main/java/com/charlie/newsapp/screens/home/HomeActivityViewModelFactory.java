package com.charlie.newsapp.screens.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.charlie.newsapp.network.NetworkStateService;
import com.charlie.newsapp.network.repo.NewsApiOrgRepo;
import com.charlie.newsapp.room.ArticleRepository;

import javax.inject.Inject;

@HomeActivityScope
public class HomeActivityViewModelFactory implements ViewModelProvider.Factory {

    private NewsApiOrgRepo newsApiOrgRepo;
    private NetworkStateService networkStateService;
    private ArticleRepository articleRepository;

    @Inject
    HomeActivityViewModelFactory(NewsApiOrgRepo newsApiOrgRepo, NetworkStateService networkStateService, ArticleRepository articleRepository) {
        this.newsApiOrgRepo = newsApiOrgRepo;
        this.networkStateService = networkStateService;
        this.articleRepository = articleRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(HomeActivityViewModel.class)) {
            return (T) new HomeActivityViewModel(newsApiOrgRepo, networkStateService, articleRepository);
        } else {
            throw new IllegalArgumentException("Can not find ViewModel class " + modelClass.getName());
        }
    }
}


