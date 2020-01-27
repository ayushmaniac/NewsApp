package com.charlie.newsapp;

import android.content.Context;

import com.charlie.newsapp.network.repo.NewsApiOrgRepo;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {
    private static final String NEWS_API_ORG = "https://newsapi.org/v2/";

    private static final String KEY = "71d18f527643450ab8a7443e5e31aa29";

    @Provides
    @ApplicationScope
    Interceptor provideInterceptorForGoogle() {
        return chain -> {
            Request original = chain.request();


            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", KEY)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    @ApplicationScope
    @Provides
    HttpLoggingInterceptor providesHttpLoggingInterceptorForGoogle() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClientForGoogle(Interceptor interceptor, HttpLoggingInterceptor httpLoggingInterceptor) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor);
        }

        return builder.build();

    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofitForGoogle(OkHttpClient client) {

        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NEWS_API_ORG)
                .client(client)
                .build();
    }

    @ApplicationScope
    @Provides
    NewsApiOrgRepo youtubeRepository(Retrofit retrofit) {
        return retrofit.create(NewsApiOrgRepo.class);
    }

    @Provides
    @ApplicationScope
    Picasso providePicasso(Context context) {
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(context, Integer.MAX_VALUE))
                .build();
        if (BuildConfig.DEBUG) {
            picasso.setIndicatorsEnabled(true);
            picasso.setLoggingEnabled(true);
        }
        return picasso;
    }


}
