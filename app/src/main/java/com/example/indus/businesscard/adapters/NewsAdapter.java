package com.example.indus.businesscard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indus.businesscard.Const;
import com.example.indus.businesscard.R;
import com.example.indus.businesscard.data.NewsItem;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    @NonNull
    private final List<NewsItem> news;
    private final SimpleDateFormat dateFormat;
    private OnRecyclerViewItemClickedListener listener;

    public NewsAdapter(OnRecyclerViewItemClickedListener listener, @NonNull List<NewsItem> news) {
        this.listener = listener;
        this.news = news;
        dateFormat = new SimpleDateFormat(Const.DATE_FORMAT);
    }

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
        newsViewHolder.bind(news.get(position), dateFormat);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public int getItemViewType(int position) {
        return news.get(position).getCategory().getId();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            itemView.setOnClickListener(this);
        }

        void bind(NewsItem newsItem, final SimpleDateFormat dateFormat) {
            Glide
                    .with(newsPhoto.getContext())
                    .load(newsItem.getImageUrl())
                    .into(newsPhoto);
            newsPublishedDate.setText(dateFormat.format(newsItem.getPublishDate()));
            newsTitle.setText(newsItem.getTitle());
            newsPreviewText.setText(newsItem.getPreviewText());
        }

        /**
         * Notify the Activity item has been clicked,
         * with newsId position
         */
        @Override
        public void onClick(View v) {
            listener.onClick(getAdapterPosition());
        }
    }


}
