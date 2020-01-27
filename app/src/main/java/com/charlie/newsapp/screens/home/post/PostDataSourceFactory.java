package com.charlie.newsapp.screens.home.post;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.charlie.newsapp.network.NetworkStateService;
import com.charlie.newsapp.network.repo.NewsApiOrgRepo;
import com.charlie.newsapp.responsemodel.PagingState;
import com.charlie.newsapp.room.ArticleRepository;

import io.reactivex.disposables.CompositeDisposable;

public class PostDataSourceFactory extends DataSource.Factory<Integer, PostModel> {

    private CompositeDisposable disposable;
    private MutableLiveData<PagingState> stateLiveData;
    private NewsApiOrgRepo newsApiOrgRepo;
    private String categoryId;
    private NetworkStateService networkStateService;
    private ArticleRepository articleRepository;

    private PostDataSource dataSource;

    public PostDataSourceFactory(CompositeDisposable disposable,
                                 MutableLiveData<PagingState> stateLiveData,
                                 NewsApiOrgRepo newsApiOrgRepo,
                                 String categoryId,
                                 NetworkStateService networkStateService,
                                 ArticleRepository articleRepository
    ) {
        this.disposable = disposable;
        this.stateLiveData = stateLiveData;
        this.newsApiOrgRepo = newsApiOrgRepo;
        this.categoryId = categoryId;
        this.networkStateService = networkStateService;
        this.articleRepository = articleRepository;
    }

    @NonNull
    @Override
    public DataSource<Integer, PostModel> create() {
        dataSource = new PostDataSource(disposable, stateLiveData, newsApiOrgRepo, categoryId,networkStateService,articleRepository);
        return dataSource;
    }

    public void invalidate() {
        dataSource.invalidate();

    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}
