package com.charlie.newsapp.screens.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.charlie.newsapp.R;

import com.charlie.newsapp.databinding.ActivityNewsBinding;

public class NewsActivity extends AppCompatActivity implements NewsActivityHandler {

    @Inject
    NewsActivityViewModelFactory viewModelFactory;

    private ActivityNewsBinding binding;

    private NewsActivityViewModel viewModel;

    private NewsActivityModel activityModel;

    private String url;
    private String img;
    private String title;
    private String date;
    private String source;
    private String author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        img = intent.getStringExtra("img");
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        source = intent.getStringExtra("source");
        author = intent.getStringExtra("author");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsActivityViewModel.class);
        activityModel = viewModel.getActivityModel();
        binding.setModel(activityModel);
        binding.setHandler(this);


        activityModel.setAuthor(author);
        activityModel.setImage(img);
        activityModel.setPublishedAt(date);
        activityModel.setUrl(url);
        activityModel.setSource(source);
        activityModel.setTitle(title);
        activityModel.setTime(date);

        activityModel.notifyChange();

        loadNews(url);
    }


    private void loadNews(String url) {
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }

    @Override
    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, activityModel.getTitle() + "\n" + activityModel.getUrl());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
