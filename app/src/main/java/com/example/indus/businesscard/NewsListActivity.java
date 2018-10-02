package com.example.indus.businesscard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.indus.businesscard.data.DataUtils;
import com.example.indus.businesscard.data.NewsItem;

import java.util.List;

public class NewsListActivity extends AppCompatActivity {
    private List<NewsItem> news;
    private RecyclerView newsRecycler;
    private NewsAdapter newsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        news = DataUtils.generateNews();
        newsRecycler = findViewById(R.id.news_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        newsAdapter = new NewsAdapter(news);
        newsRecycler.setAdapter(newsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_me:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
