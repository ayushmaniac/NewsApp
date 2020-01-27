package com.charlie.newsapp.screens.home.categoryadapter;

import androidx.recyclerview.widget.RecyclerView;

import com.charlie.newsapp.databinding.NewHorizontalBinding;




class CategoryViewHolder extends RecyclerView.ViewHolder {

    private final NewHorizontalBinding binding;

    CategoryViewHolder(NewHorizontalBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public NewHorizontalBinding getBinding() {
        return binding;
    }
}
