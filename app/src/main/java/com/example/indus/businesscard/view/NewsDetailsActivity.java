package com.example.indus.businesscard.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.indus.businesscard.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewsDetailsActivity extends AppCompatActivity {
    private final static String NEWS_URL = "news_url";

    private WebView newsDetails;

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
        newsDetails = findViewById(R.id.news_details);
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setViewData() {
        Intent intent = getIntent();
        String detailsUrl = intent.getStringExtra(NEWS_URL);
        newsDetails.loadUrl(detailsUrl);
    }

    public static void start(@NonNull Context context, String newsUrl) {
        Intent openNewsDetails = new Intent(context, NewsDetailsActivity.class);
        openNewsDetails.putExtra(NEWS_URL, newsUrl);
        context.startActivity(openNewsDetails);
    }
}
