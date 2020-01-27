package com.charlie.newsapp.bindingadapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;


public class BindingAdapters {

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(imageUrl).into(view);
    }

    @BindingAdapter({"android:src"})
    public static void loadImage(ImageView view, int i) {
        view.setImageResource(i);
    }

    @BindingAdapter({"bind:textcolor"})
    public static void setImageViewTint(TextView view, int resource) {
        view.setTextColor(view.getContext().getResources().getColor(resource));
    }

    @BindingAdapter({"bind:bg_drawable"})
    public static void setTextBackGroundResource(TextView view, int resource) {
        view.setBackground(ContextCompat.getDrawable(view.getContext(), resource));
    }

    @BindingAdapter({"bind:refreshing"})
    public static void setSwipeLayoutRefreshing(SwipeRefreshLayout view, boolean refreshing) {
        view.setRefreshing(refreshing);
    }


    @BindingAdapter("bind:goneVisibility")
    public static void goneVisibility(View view, boolean visibility) {
        if (visibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("bind:inVisibility")
    public static void inVisibility(View view, boolean visibility) {
        if (visibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

}
