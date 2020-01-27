package com.charlie.newsapp.network.repo;


import com.charlie.newsapp.network.models.News;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiOrgRepo {

    @GET("top-headlines")
    Single<News> getNews(
            @Query("country") String country,
            @Query("page") int page
    );

    @GET("top-headlines")
    Single<News> getNewsByCategory(
            @Query("country") String country,
            @Query("page") int page,
            @Query("category") String category
    );

}
