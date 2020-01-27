package com.charlie.newsapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;



@Database(entities = {Article.class}, version = 1)

public abstract class ArticleDatabase extends RoomDatabase {

    public abstract ArticleDao getArticleDatabase();


}
