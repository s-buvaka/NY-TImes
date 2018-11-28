package com.example.indus.businesscard.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.indus.businesscard.R;
import com.example.indus.businesscard.database.NewsDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsActivity extends AppCompatActivity {
    private final static String NEWS_URL = "news_url";
    private final static String NEWS_ID = "news_id";

    private CompositeDisposable compositeDisposable;
    private WebView newsDetails;
    private NewsDatabase newsDatabase;
    private int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        init();
        createToolbar();
        getIntentData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_details_menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.about_me_menu_button:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.delete_menu_button:
                deleteFromDB(newsId);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        compositeDisposable = new CompositeDisposable();
        newsDatabase = NewsDatabase.getAppDatabase(this);
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

    private void getIntentData() {
        Intent intent = getIntent();
        String detailsUrl = intent.getStringExtra(NEWS_URL);
        newsId = intent.getIntExtra(NEWS_ID, -1);
        newsDetails.loadUrl(detailsUrl);
    }

    public static void start(@NonNull Context context, String newsUrl, int newsId) {
        Intent openNewsDetails = new Intent(context, NewsDetailsActivity.class);
        openNewsDetails.putExtra(NEWS_URL, newsUrl);
        openNewsDetails.putExtra(NEWS_ID, newsId);
        context.startActivity(openNewsDetails);
    }

    private void deleteFromDB(int id) {
        compositeDisposable.add(
                Completable.fromAction(() -> newsDatabase.getNewsDao().deleteById(id))
                .subscribeOn(Schedulers.io())
                .subscribe());
    }
}
