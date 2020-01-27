package com.charlie.newsapp.room;


import com.charlie.newsapp.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

@ApplicationScope
public class ArticleRepository {

    private ArticleDao articleDao;

    @Inject
    public ArticleRepository(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public Single<Long> insert(Article article) {

        Single<Long> single = Single.create(emitter -> {
            try {
                long id = articleDao.insert(article);
                emitter.onSuccess(id);
            } catch (Exception error) {
                emitter.onError(error);
            }
        });
        return single;
    }

    public Completable deleteAll() {
        return Completable.create(emitter -> {
            try {
                articleDao.deleteAll();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public Single<List<Article>> getArticleList() {
        Single<List<Article>> listSingle = Single.create(emitter -> {
            try {
                emitter.onSuccess(articleDao.getArticleList());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        return listSingle;
    }

}
