package com.charlie.newsapp.screens.home.post;

import androidx.recyclerview.widget.RecyclerView;

import com.charlie.newsapp.databinding.PostListItemBinding;

public class PostViewHolder extends RecyclerView.ViewHolder {

    private PostListItemBinding binding;

    PostViewHolder(PostListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public PostListItemBinding getBinding() {
        return binding;
    }

    public void bindTo(PostModel postModel) {
        binding.setModel(postModel);
    }
}
