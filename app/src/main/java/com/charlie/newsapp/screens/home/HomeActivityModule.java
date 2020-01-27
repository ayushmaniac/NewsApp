package com.charlie.newsapp.screens.home;

import com.charlie.newsapp.adapter.feed.FeedAdapter;
import com.charlie.newsapp.screens.home.categoryadapter.CategoryAdapter;
import com.charlie.newsapp.screens.home.post.PostAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeActivityModule {


    @Provides
    @HomeActivityScope
    PostAdapter postAdapter() {
        return new PostAdapter();
    }

    @Provides
    @HomeActivityScope
    CategoryAdapter categoryAdapter() {
        return new CategoryAdapter();
    }
}
