package com.example.indus.businesscard.adapters;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indus.businesscard.Const;
import com.example.indus.businesscard.R;
import com.example.indus.businesscard.data.NewsItem;
import com.example.indus.businesscard.view.NewsDetailsActivity;

import java.text.SimpleDateFormat;

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

    void bind (NewsItem newsItem, final int newsId){
        Glide
                .with(newsPhoto.getContext())
                .load(newsItem.getImageUrl())
                .into(newsPhoto);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT);
        newsPublishedDate.setText(dateFormat.format(newsItem.getPublishDate()));
        newsTitle.setText(newsItem.getTitle());
        newsPreviewText.setText(newsItem.getPreviewText());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openNewsDetails = new Intent(view.getContext(), NewsDetailsActivity.class);
                openNewsDetails.putExtra(Const.NEWS_ID, newsId);
                view.getContext().startActivity(openNewsDetails);
            }
        });
    }
}
