package com.charlie.newsapp.screens.home.post;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.charlie.newsapp.databinding.PostListItemBinding;

public class PostAdapter extends PagedListAdapter<PostModel, PostViewHolder> {

    private static DiffUtil.ItemCallback<PostModel> itemCallback = new DiffUtil.ItemCallback<PostModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostModel oldItem, @NonNull PostModel newItem) {
            return oldItem.getUrl().equals(newItem.getUrl());//TODO
        }

        @Override
        public boolean areContentsTheSame(@NonNull PostModel oldItem, @NonNull PostModel newItem) {
            return oldItem.getUrl().equals(newItem.getUrl());//TODO
        }
    };
    private LayoutInflater inflater;
    private OnItemClickListener clickListener;


    public PostAdapter() {
        super(itemCallback);
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        PostListItemBinding binding = PostListItemBinding.inflate(inflater, parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        final PostModel model = getItem(position);

        holder.getBinding().setModel(model);
        holder.bindTo(model);
        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.onClick(model);
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(PostModel postModel);
    }
}

