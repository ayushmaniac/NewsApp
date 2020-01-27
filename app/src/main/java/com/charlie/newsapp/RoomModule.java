package com.charlie.newsapp;

import android.content.Context;

import androidx.room.Room;

import com.charlie.newsapp.room.Article;
import com.charlie.newsapp.room.ArticleDao;
import com.charlie.newsapp.room.ArticleDatabase;

import dagger.Module;
import dagger.Provides;



@Module()
public class RoomModule {

    @ApplicationScope
    @Provides
    ArticleDatabase providesNewsDatabase(Context context) {
        return Room.databaseBuilder(context, ArticleDatabase.class, "news_database").build();

    }

    @ApplicationScope
    @Provides
    ArticleDao providesNewResultDao(ArticleDatabase newsDatabase) {
        return newsDatabase.getArticleDatabase();
    }


}