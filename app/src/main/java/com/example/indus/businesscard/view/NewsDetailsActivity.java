package com.example.indus.businesscard.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indus.businesscard.utils.Const;
import com.example.indus.businesscard.R;
import com.example.indus.businesscard.data.DataUtils;
import com.example.indus.businesscard.data.NewsItem;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewsDetailsActivity extends AppCompatActivity {
    private final static String NEWS_ID = "news_id";

    private List<NewsItem> news;

    private ImageView detailsPhoto;
    private TextView detailsTitle;
    private TextView detailsPublishedDate;
    private TextView detailsOverviewText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        initView();
        createToolbar();
        setViewData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.action_switch:
                Log.d("MyLogs", "click");
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        detailsPhoto = findViewById(R.id.details_photo);
        detailsTitle = findViewById(R.id.details_title);
        detailsPublishedDate = findViewById(R.id.details_published_date);
        detailsOverviewText = findViewById(R.id.details_overview);
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setViewData() {
        news = DataUtils.generateNews();
        Intent intent = getIntent();
        int newsId = intent.getIntExtra(NEWS_ID, -1);

        Glide
                .with(this)
                .load(news.get(newsId).getImageUrl())
                .into(detailsPhoto);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT);
        detailsPublishedDate.setText(dateFormat.format(news.get(newsId).getPublishDate()));
        detailsTitle.setText(news.get(newsId).getTitle());
        detailsOverviewText.setText(news.get(newsId).getFullText());

    }

    public static void start(@NonNull Context context, @NonNull int newsId) {
        Intent openNewsDetails = new Intent(context, NewsDetailsActivity.class);
        openNewsDetails.putExtra(NEWS_ID, newsId);
        context.startActivity(openNewsDetails);
    }
}
