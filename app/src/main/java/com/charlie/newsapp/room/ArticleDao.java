package com.charlie.newsapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    Long insert(Article article);

    @Query("DELETE FROM article")
    void deleteAll();

    @Query("SELECT * from article ORDER BY timestamp ASC")
    List<Article> getArticleList();

    @Query("SELECT * from article WHERE id = :id")
    Article loadArticle(long id);

}
