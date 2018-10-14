package com.example.indus.businesscard.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indus.businesscard.Const;
import com.example.indus.businesscard.R;
import com.example.indus.businesscard.data.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    @NonNull
    private final List<NewsItem> news;

    public NewsAdapter(@NonNull List<NewsItem> news) {
        this.news = news;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case Const.CATEGORY_DARWIN_AWARDS:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_news_darwin_awards, viewGroup, false);
                return new NewsViewHolder(view);
            case Const.CATEGORY_CRIMINAL:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_news_criminal, viewGroup, false);
                return new NewsViewHolder(view);
            case Const.CATEGORY_ANIMALS:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_news_animals, viewGroup, false);
                return new NewsViewHolder(view);
            case Const.CATEGORY_MUSIC:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_news_music, viewGroup, false);
                return new NewsViewHolder(view);
            default:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_news_darwin_awards, viewGroup, false);
                return new NewsViewHolder(view);

        }
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
        switch (news.get(position).getCategory().getId()) {
            case Const.CATEGORY_DARWIN_AWARDS:
                return Const.CATEGORY_DARWIN_AWARDS;
            case Const.CATEGORY_CRIMINAL:
                return Const.CATEGORY_CRIMINAL;
            case Const.CATEGORY_ANIMALS:
                return Const.CATEGORY_ANIMALS;
            case Const.CATEGORY_MUSIC:
                return Const.CATEGORY_MUSIC;
        }
        return super.getItemViewType(position);
    }
}
