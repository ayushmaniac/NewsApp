package com.charlie.newsapp.screens.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.charlie.newsapp.R;
import com.charlie.newsapp.databinding.ActivityHomeBinding;
import com.charlie.newsapp.responsemodel.PagingState;
import com.charlie.newsapp.responsemodel.Response;
import com.charlie.newsapp.screens.home.categoryadapter.CategoryAdapter;
import com.charlie.newsapp.screens.home.categoryadapter.CategoryModel;
import com.charlie.newsapp.screens.home.post.PostAdapter;
import com.charlie.newsapp.screens.news.NewsActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


public class HomeActivity extends AppCompatActivity implements HomeActivityHandler, CategoryAdapter.OnItemClickListener {

    @Inject
    HomeActivityViewModelFactory viewModelFactory;

    @Inject
    PostAdapter postAdapter;

    @Inject
    CategoryAdapter categoryAdapter;

    private ActivityHomeBinding binding;

    private HomeActivityViewModel viewModel;

    private HomeActivityModel activityModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(HomeActivityViewModel.class);
        activityModel = viewModel.getActivityModel();
        binding.setModel(activityModel);
        binding.setHandler(this);

        binding.recyclerView.setAdapter(postAdapter);
        binding.categoryRecycler.setAdapter(categoryAdapter);

        categoryAdapter.setItemClickListener(this);

        postAdapter.setClickListener(postModel -> {

            Intent intent = new Intent(this, NewsActivity.class);

            intent.putExtra("url", postModel.getUrl());
            intent.putExtra("title", postModel.getTitle());
            intent.putExtra("img", postModel.getImage());
            intent.putExtra("date", postModel.getPublishedAt());
            intent.putExtra("source", postModel.getSource());
            intent.putExtra("author", postModel.getAuthor());

            startActivity(intent);
        });
//        viewModel.getFeedResponse().observe(this, this::onFeedResponse);


        viewModel.getPagedListStateLiveData().observe(this, pagingState -> {
            if (pagingState.getState() == PagingState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
//                 binding.noFeeds.setVisibility(View.GONE);
                //todo
            } else if (pagingState.getState() == PagingState.Success) {

                binding.swipeRefresh.setRefreshing(false);
                binding.recyclerView.setVisibility(View.VISIBLE);
                //todo

            } else if (pagingState.getState() == PagingState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                //todo
            }
        });

        viewModel.getPagedListLiveData().observe(this, postModels -> {
            postAdapter.submitList(postModels);
            if (postModels.isEmpty()) {
                // binding.recycler.setVisibility(View.GONE);
                // binding.noFeeds.setVisibility(View.VISIBLE);

            } else {
                //  binding.recycler.setVisibility(View.VISIBLE);
                //  binding.noFeeds.setVisibility(View.GONE);

            }
        });

        viewModel.getCategoryResponse().observe(this, this::observeCategoryResponse);

        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.invalidateListData());

    }

    private void observeCategoryResponse(Response<List<CategoryModel>> response) {

        categoryAdapter.setData(response.getData(), 0);

    }

    @Override
    public void onClick(CategoryModel category, int position) {
        viewModel.setCategoryId(category.getId());
        viewModel.invalidateListData();
        binding.recyclerView.setVisibility(View.GONE);
        binding.recyclerView.scrollToPosition(0);

    }


}
