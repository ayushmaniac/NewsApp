package com.charlie.newsapp;


import com.charlie.newsapp.screens.home.HomeActivity;
import com.charlie.newsapp.screens.home.HomeActivityModule;
import com.charlie.newsapp.screens.home.HomeActivityScope;
import com.charlie.newsapp.screens.news.NewsActivity;
import com.charlie.newsapp.screens.news.NewsActivityModule;
import com.charlie.newsapp.screens.news.NewsActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@SuppressWarnings("unused")
@Module
abstract class ActivityProvider {
    @ContributesAndroidInjector(modules = {HomeActivityModule.class})
    @HomeActivityScope
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = {NewsActivityModule.class})
    @NewsActivityScope
    abstract NewsActivity bindNewsActivity();
}
