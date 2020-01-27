package com.charlie.newsapp.adapter.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.charlie.newsapp.databinding.FeedListItemBinding;

import java.util.List;

import javax.inject.Inject;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private LayoutInflater inflater;
    private List<FeedModel> feedModels;
    private OnItemClickListener clickListener;

    public FeedAdapter() {
    }

    public void setFeedModels(List<FeedModel> feedModels) {
        this.feedModels = feedModels;
        notifyDataSetChanged();
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        FeedListItemBinding binding = FeedListItemBinding.inflate(inflater, parent, false);

        return new FeedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        FeedModel feedModel = feedModels.get(position);

        holder.getBinding().setModel(feedModel);
        holder.bindTo(feedModel);
        holder.itemView.setOnClickListener(view -> clickListener.onClick(feedModel));

    }

    @Override
    public int getItemCount() {
        return feedModels != null ? feedModels.size() : 0;
    }

    public interface OnItemClickListener {
        void onClick(FeedModel feedModel);
    }
}

