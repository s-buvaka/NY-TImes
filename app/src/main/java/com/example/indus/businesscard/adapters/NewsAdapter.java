package com.example.indus.businesscard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.indus.businesscard.R;
import com.example.indus.businesscard.modeldto.NewsEntity;
import com.example.indus.businesscard.utils.Const;
import com.example.indus.businesscard.view.NewsDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    @NonNull
    private final List<NewsEntity> news = new ArrayList<>();

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId = R.layout.item_news_default;
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutId, viewGroup, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {
        newsViewHolder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void replaceItems(List<NewsEntity> newsEntities) {
        news.clear();
        news.addAll(newsEntities);
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView newsPhoto;
        private final TextView newsTitle;
        private final TextView newsPreviewText;
        private final TextView newsPublishedDate;
        private final TextView newsCategory;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsPhoto = itemView.findViewById(R.id.item_photo);
            newsTitle = itemView.findViewById(R.id.item_title);
            newsPreviewText = itemView.findViewById(R.id.item_preview_text);
            newsPublishedDate = itemView.findViewById(R.id.item_published_date);
            newsCategory = itemView.findViewById(R.id.item_category);
        }


        void bind(NewsEntity newsEntity) {
            setImage(newsEntity, newsPhoto);
            setDate(newsEntity, newsPublishedDate);
            setCategory(newsEntity, newsCategory);
            newsTitle.setText(newsEntity.getTitle());
            newsPreviewText.setText(newsEntity.getPreviewText());
            itemView.setOnClickListener(view ->
                    NewsDetailsActivity.start(view.getContext(), newsEntity.getUrl(), newsEntity.getId()));
        }
    }

    private void setImage(NewsEntity item, ImageView targetImageView) {
        if (!item.getMultimedia().isEmpty()) {
            Glide
                    .with(targetImageView.getContext())
                    .load(item.getMultimedia().get(0).getUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.error_image))
                    .into(targetImageView);
        }
    }

    private void setDate(NewsEntity item, TextView view) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(Const.INPUT_DATE_FORMAT, Locale.ENGLISH);
        String publishedDate = item.getPublishedDate();
        try {
            Date date = inputDateFormat.parse(publishedDate);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(Const.OUTPUT_DATE_FORMAT, Locale.ENGLISH);
            String output = outputDateFormat.format(date);
            view.setText(output);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setCategory(NewsEntity item, TextView view) {
        if (item.getSubsection() != null) {
            view.setText(item.getSubsection());
        } else {
            view.setVisibility(View.GONE);
        }
    }
}