package com.example.indus.businesscard.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.indus.businesscard.R;
import com.example.indus.businesscard.adapters.NewsAdapter;
import com.example.indus.businesscard.adapters.NewsItemDecorator;
import com.example.indus.businesscard.data.DataUtils;
import com.example.indus.businesscard.data.NewsItem;
import com.example.indus.businesscard.utils.Const;
import com.example.indus.businesscard.utils.Utils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListActivity extends AppCompatActivity {
    private static final int SPAN_COUNT = 2;
    private static final int SPACE_ITEM_DECORATION = 4;

    private NewsAdapter newsAdapter;
    private Disposable disposable;

    private RecyclerView newsRecycler;
    private Toolbar toolbar;
    private ProgressBar progress;
    private View error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        initView();
        createRecycler();
        setSupportActionBar(toolbar);
        loadItems();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsAdapter = null;
        newsRecycler = null;
        progress = null;
        disposable.dispose();
        disposable = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                Log.d("MyLogs", "click");
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initView() {
        error = findViewById(R.id.error_layout);
        progress = findViewById(R.id.progress_bar);
        newsRecycler = findViewById(R.id.news_recycler_view);
        toolbar = findViewById(R.id.toolbar);
    }

    private void createRecycler() {
        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        }
        newsRecycler.addItemDecoration(new NewsItemDecorator(this, SPACE_ITEM_DECORATION));
        newsRecycler.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter();
        newsRecycler.setAdapter(newsAdapter);
    }

    private void loadItems() {
        showProgress(true);
        disposable = Observable.fromCallable(DataUtils::generateNews)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateItems,
                        this::handleError);
    }

    private void updateItems(List<NewsItem> news) {
        if (newsAdapter != null) {
            newsAdapter.replaceItems(news);
        }
        Utils.setVisible(newsRecycler, true);
        Utils.setVisible(progress, false);
        Utils.setVisible(error, false);
    }

    private void showProgress(boolean isShow) {
        Utils.setVisible(progress, isShow);
        Utils.setVisible(error, !isShow);
        Utils.setVisible(newsRecycler, !isShow);
    }

    private void handleError(Throwable th) {
        Log.e(Const.LOG_TAG, th.getMessage(), th);
        Utils.setVisible(error, true);
        Utils.setVisible(progress, false);
        Utils.setVisible(newsRecycler, false);

    }
}
