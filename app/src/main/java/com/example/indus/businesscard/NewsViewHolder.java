package com.example.indus.businesscard;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indus.businesscard.data.NewsItem;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private final ImageView newsPhoto;
    private final TextView newsTitle;
    private final TextView newsPreviewText;
    private final TextView newsPublishedDate;


    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        newsPhoto = itemView.findViewById(R.id.item_photo);
        newsTitle = itemView.findViewById(R.id.item_title);
        newsPreviewText = itemView.findViewById(R.id.item_preview_text);
        newsPublishedDate = itemView.findViewById(R.id.item_published_date);
    }

    void bind (NewsItem newsItem){
        newsPhoto.setImageURI(Uri.parse(newsItem.getImageUrl()));
        newsTitle.setText(newsItem.getTitle());
        newsPreviewText.setText(newsItem.getPreviewText());
        newsPublishedDate.setText(newsItem.getPublishDate().toString());
    }
}
