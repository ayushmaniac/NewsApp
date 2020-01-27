package com.charlie.newsapp.screens.home.post;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;

import com.charlie.newsapp.network.NetworkStateService;
import com.charlie.newsapp.network.models.Article;
import com.charlie.newsapp.network.models.News;
import com.charlie.newsapp.network.repo.NewsApiOrgRepo;
import com.charlie.newsapp.responsemodel.PagingState;
import com.charlie.newsapp.room.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class PostDataSource extends ItemKeyedDataSource<Integer, PostModel> {

    private CompositeDisposable disposable;
    private MutableLiveData<PagingState> stateLiveData;
    private NewsApiOrgRepo newsApiOrgRepo;
    private String categoryId;
    private NetworkStateService networkStateService;
    private ArticleRepository articleRepository;

    private int page = 1;

    private boolean database = false;

    PostDataSource(
            @NonNull CompositeDisposable disposable,
            @NonNull MutableLiveData<PagingState> stateLiveData,
            NewsApiOrgRepo newsApiOrgRepo,
            String categoryId,
            NetworkStateService networkStateService, ArticleRepository articleRepository) {

        this.disposable = disposable;
        this.stateLiveData = stateLiveData;
        this.newsApiOrgRepo = newsApiOrgRepo;
        this.categoryId = categoryId;
        this.networkStateService = networkStateService;
        this.articleRepository = articleRepository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<PostModel> callback) {
        stateLiveData.postValue(PagingState.loading());

        Single<News> single;

        if (networkStateService.isNetworkAvailable()) {


            if (categoryId == null) {
                single = newsApiOrgRepo.getNews("in", 1);
            } else {
                single = newsApiOrgRepo.getNewsByCategory("in", 1, categoryId);
            }

            disposable.add(single.map(news -> {
                articleRepository.deleteAll().subscribe();
                for (Article article : news.getArticle()) {
                    articleRepository.insert(getArticle(article)).subscribe();
                }
                return getPostModel(news);
            })
                    .subscribe(result -> {
                                callback.onResult(result);
                                page = 2;
                                stateLiveData.postValue(PagingState.success());
                            }, error -> {
                                stateLiveData.postValue(PagingState.error(error));
                            }
                    ));
        } else {
            disposable.add(articleRepository.getArticleList().map(articles -> getPostModel(articles)).subscribe(postModels -> {
                callback.onResult(postModels);
                page = 2;
                stateLiveData.postValue(PagingState.success());
            }, throwable -> {
                stateLiveData.postValue(PagingState.error(throwable));
            }));
            database = true;
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<PostModel> callback) {
        stateLiveData.postValue(PagingState.loading());
        Single<News> single;
        if (database) {
            return;
        }
        if (categoryId == null) {
            single = newsApiOrgRepo.getNews("in", page);
        } else {
            single = newsApiOrgRepo.getNewsByCategory("in", page, categoryId);
        }
        disposable.add(single.map(news -> {
            for (Article article : news.getArticle()) {
                articleRepository.insert(getArticle(article)).subscribe();
            }
            return getPostModel(news);
        }).subscribe(result -> {
                    callback.onResult(result);
                    page++;
                    stateLiveData.postValue(PagingState.success());
                }, error -> {
                    stateLiveData.postValue(PagingState.error(error));
                }
        ));
    }

    private com.charlie.newsapp.room.Article getArticle(Article article) {
        com.charlie.newsapp.room.Article postModel = new com.charlie.newsapp.room.Article();


        postModel.setTitle(article.getTitle());
        postModel.setAuthor(article.getAuthor());
        postModel.setUrlToImage(article.getUrlToImage());
        postModel.setPublishedAt(article.getPublishedAt());
        postModel.setSource(article.getSource().getName());
        postModel.setUrl(article.getUrl());
        postModel.setDescription(article.getDescription());
        postModel.setTimestamp(System.currentTimeMillis());

        return postModel;
    }

    private static List<PostModel> getPostModel(List<com.charlie.newsapp.room.Article> articles) {
        List<PostModel> postModels = new ArrayList<>();

        for (com.charlie.newsapp.room.Article article : articles) {
            PostModel postModel = new PostModel();

            postModel.setTitle(article.getTitle());
            postModel.setAuthor(article.getAuthor());
            postModel.setImage(article.getUrlToImage());
            postModel.setPublishedAt(article.getPublishedAt());
            postModel.setSource(article.getSource());
            postModel.setUrl(article.getUrl());

            postModels.add(postModel);
        }
        return postModels;
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<PostModel> callback) {
        // do nothing
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull PostModel item) {
        return item.getId();
    }

    private static List<PostModel> getPostModel(News news) {

        List<PostModel> postModels = new ArrayList<>();

        for (Article article : news.getArticle()) {
            PostModel postModel = new PostModel();

            postModel.setTitle(article.getTitle());
            postModel.setAuthor(article.getAuthor());
            postModel.setImage(article.getUrlToImage());
            postModel.setPublishedAt(article.getPublishedAt());
            postModel.setSource(article.getSource().getName());
            postModel.setUrl(article.getUrl());

            postModels.add(postModel);
        }
        return postModels;
    }
}
