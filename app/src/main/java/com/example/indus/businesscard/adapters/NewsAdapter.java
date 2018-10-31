package com.example.indus.businesscard.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indus.businesscard.utils.Const;
import com.example.indus.businesscard.R;
import com.example.indus.businesscard.data.NewsItem;
import com.example.indus.businesscard.view.NewsDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    @NonNull
    private final List<NewsItem> news = new ArrayList<>();

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        int layoutId;
        switch (viewType) {
            case Const.CATEGORY_DARWIN_AWARDS:
               layoutId = R.layout.item_news_darwin_awards;
               break;
            case Const.CATEGORY_CRIMINAL:
                layoutId = R.layout.item_news_criminal;
                break;
            case Const.CATEGORY_ANIMALS:
                layoutId = R.layout.item_news_animals;
                break;
            case Const.CATEGORY_MUSIC:
                layoutId = R.layout.item_news_music;
                break;
            default:
                layoutId = R.layout.item_news_darwin_awards;
                break;

        }
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutId, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {
        newsViewHolder.bind(news.get(position), position);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public int getItemViewType(int position) {
        return news.get(position).getCategory().getId();
    }


    public void replaceItems(List<NewsItem> newsItems) {
        news.clear();
        news.addAll(newsItems);
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView newsPhoto;
        private final TextView newsTitle;
        private final TextView newsPreviewText;
        private final TextView newsPublishedDate;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsPhoto = itemView.findViewById(R.id.item_photo);
            newsTitle = itemView.findViewById(R.id.item_title);
            newsPreviewText = itemView.findViewById(R.id.item_preview_text);
            newsPublishedDate = itemView.findViewById(R.id.item_published_date);
        }


        void bind (NewsItem newsItem, final int newsId){
            Glide
                    .with(newsPhoto.getContext())
                    .load(newsItem.getImageUrl())
                    .into(newsPhoto);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT);
            newsPublishedDate.setText(dateFormat.format(newsItem.getPublishDate()));
            newsTitle.setText(newsItem.getTitle());
            newsPreviewText.setText(newsItem.getPreviewText());

            itemView.setOnClickListener(view -> NewsDetailsActivity.start(view.getContext(), newsId));
        }
    }
}
