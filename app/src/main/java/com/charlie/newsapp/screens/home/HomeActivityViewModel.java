package com.charlie.newsapp.screens.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.charlie.newsapp.R;
import com.charlie.newsapp.network.NetworkStateService;
import com.charlie.newsapp.network.repo.NewsApiOrgRepo;
import com.charlie.newsapp.responsemodel.PagingState;
import com.charlie.newsapp.responsemodel.Response;
import com.charlie.newsapp.room.ArticleRepository;
import com.charlie.newsapp.screens.home.categoryadapter.CategoryModel;
import com.charlie.newsapp.screens.home.post.PostDataSourceFactory;
import com.charlie.newsapp.screens.home.post.PostModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class HomeActivityViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private HomeActivityModel activityModel = new HomeActivityModel();
    private NewsApiOrgRepo newsApiOrgRepo;
    private NetworkStateService networkStateService;
    private ArticleRepository articleRepository;

    private MutableLiveData<Response> feedResponse = new MutableLiveData<>();

    private MutableLiveData<Response> categoryResponse = new MutableLiveData<>();
    private String categoryId;


    public HomeActivityViewModel(NewsApiOrgRepo newsApiOrgRepo, NetworkStateService networkStateService, ArticleRepository articleRepository) {
        this.newsApiOrgRepo = newsApiOrgRepo;
        this.networkStateService = networkStateService;
        this.articleRepository = articleRepository;
        load();
        prepare();
        loadCategories();

    }

    private void loadCategories() {
        List<CategoryModel> categoryModels = new ArrayList<>();

        categoryModels.add(new CategoryModel(R.mipmap.ic_launcher, "General", true, "general"));
        categoryModels.add(new CategoryModel(R.mipmap.ic_launcher, "Entertainment", false, "entertainment"));
        categoryModels.add(new CategoryModel(R.mipmap.ic_launcher, "Business", false, "business"));
        categoryModels.add(new CategoryModel(R.mipmap.ic_launcher, "Health", false, "health"));
        categoryModels.add(new CategoryModel(R.mipmap.ic_launcher, "Science", false, "science"));
        categoryModels.add(new CategoryModel(R.mipmap.ic_launcher, "Sports", false, "sports"));
        categoryModels.add(new CategoryModel(R.mipmap.ic_launcher, "Technology", false, "technology"));

        categoryResponse.setValue(Response.success(categoryModels));
    }

    public MutableLiveData<Response> getCategoryResponse() {
        return categoryResponse;
    }

    private MutableLiveData<PagingState> pagedListStateLiveData = new MutableLiveData<>();
    private PostDataSourceFactory postDataSourceFactory;
    private LiveData<PagedList<PostModel>> pagedListLiveData;

    MutableLiveData<PagingState> getPagedListStateLiveData() {
        return pagedListStateLiveData;
    }

    LiveData<PagedList<PostModel>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    void invalidateListData() {
        postDataSourceFactory.invalidate();
    }

    void prepare() {
        postDataSourceFactory = new PostDataSourceFactory(disposable, pagedListStateLiveData, newsApiOrgRepo,categoryId,networkStateService, articleRepository);

        PagedList.Config build = new PagedList.Config.Builder().setPrefetchDistance(20).build();

        pagedListLiveData = new LivePagedListBuilder<>(postDataSourceFactory, build).build();
    }


    public HomeActivityModel getActivityModel() {
        return activityModel;
    }

    public MutableLiveData<Response> getFeedResponse() {
        return feedResponse;
    }


    void load() {
   /*     disposable.add(
                newsApiOrgRepo.getNews("in")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(news -> {
                            feedResponse.setValue(Response.success(news));
                        }, throwable -> {

                        })
        );*/

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        postDataSourceFactory.setCategoryId(categoryId);
    }

    public String getCategoryId() {
        return categoryId;
    }
}
