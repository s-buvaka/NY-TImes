package com.example.indus.businesscard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indus.businesscard.Const;
import com.example.indus.businesscard.R;
import com.example.indus.businesscard.data.DataUtils;
import com.example.indus.businesscard.data.NewsItem;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsDetailsActivity extends AppCompatActivity {

    private List<NewsItem> news;

    private ImageView detailsPhoto;
    private TextView detailsTitle;
    private TextView detailsPublishedDate;
    private TextView detailsOverviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        initView();

        news = DataUtils.generateNews();
        Intent intent = getIntent();
        int newsId = intent.getIntExtra(Const.NEWS_ID, -1);

        Glide
                .with(this)
                .load(news.get(newsId).getImageUrl())
                .into(detailsPhoto);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT);
        detailsPublishedDate.setText(dateFormat.format(news.get(newsId).getPublishDate()));
        detailsTitle.setText(news.get(newsId).getTitle());
        detailsOverviewText.setText(news.get(newsId).getFullText());

    }

    private void initView() {
        detailsPhoto = findViewById(R.id.details_photo);
        detailsTitle = findViewById(R.id.details_title);
        detailsPublishedDate = findViewById(R.id.details_published_date);
        detailsOverviewText = findViewById(R.id.details_overview);
    }
}
