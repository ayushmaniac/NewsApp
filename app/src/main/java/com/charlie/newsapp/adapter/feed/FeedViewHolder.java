package com.charlie.newsapp.adapter.feed;

import androidx.recyclerview.widget.RecyclerView;

import com.charlie.newsapp.databinding.FeedListItemBinding;

public class FeedViewHolder extends RecyclerView.ViewHolder {

    private FeedListItemBinding binding;

    FeedViewHolder(FeedListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public FeedListItemBinding getBinding() {
        return binding;
    }

    public void bindTo(FeedModel feedModel) {
        binding.setModel(feedModel);
    }
}
