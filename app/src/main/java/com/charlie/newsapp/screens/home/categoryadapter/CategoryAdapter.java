package com.charlie.newsapp.screens.home.categoryadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.charlie.newsapp.R;
import com.charlie.newsapp.databinding.NewHorizontalBinding;

import java.util.List;

import javax.inject.Inject;



public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private LayoutInflater layoutInflater;
    private OnItemClickListener itemClickListener;

    private List<CategoryModel> categories;

    private int indicator;

    public CategoryAdapter() {
    }

    public void setData(List<CategoryModel> categories, int indicatorPosition) {
        this.categories = categories;
        this.indicator = indicatorPosition;
        notifyDataSetChanged();
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        CategoryModel category = categories.get(position);

        holder.getBinding().setModel(category);
        holder.itemView.setOnClickListener(view -> {
            if (itemClickListener != null) {
                itemClickListener.onClick(category, position);


            }
            if (indicator != -1) {
                categories.get(indicator).setIndicator(false);
            }
            category.setIndicator(true);
            indicator = position;
        });
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        NewHorizontalBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.new_horizontal, viewGroup, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    public interface OnItemClickListener {
        void onClick(CategoryModel category, int position);
    }

}
