package com.example.indus.businesscard.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.indus.businesscard.adapters.NewsAdapter;
import com.example.indus.businesscard.R;
import com.example.indus.businesscard.adapters.NewsItemDecorator;
import com.example.indus.businesscard.data.DataUtils;
import com.example.indus.businesscard.data.NewsItem;

import java.util.List;

public class NewsListActivity extends AppCompatActivity {
    private List<NewsItem> news;
    private RecyclerView newsRecycler;
    private NewsAdapter newsAdapter;
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        createRecycler();
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
                Log.d("MyLogs", "click");
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void createRecycler(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new GridLayoutManager(this, 2);
        }
        news = DataUtils.generateNews();
        newsRecycler = findViewById(R.id.news_recycler_view);
        newsRecycler.addItemDecoration(new NewsItemDecorator(4));
        newsRecycler.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(news);
        newsRecycler.setAdapter(newsAdapter);
    }
}
