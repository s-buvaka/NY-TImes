package com.example.indus.businesscard.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.indus.businesscard.R;
import com.example.indus.businesscard.adapters.NewsAdapter;
import com.example.indus.businesscard.adapters.NewsItemDecorator;
import com.example.indus.businesscard.database.NewsDatabase;
import com.example.indus.businesscard.database.NewsTypeConverter;
import com.example.indus.businesscard.modeldto.NewsEntity;
import com.example.indus.businesscard.network.INewsEndPoint;
import com.example.indus.businesscard.network.RestApi;
import com.example.indus.businesscard.utils.Const;
import com.example.indus.businesscard.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListActivity extends AppCompatActivity {
    private static final int SPAN_COUNT = 2;
    private static final int SPACE_ITEM_DECORATION = 4;
    private static final String SELECTED_CATEGORY = "selected_category";

    private NewsAdapter newsAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private INewsEndPoint endPoint;
    private NewsDatabase newsDatabase;

    private RecyclerView newsRecycler;
    private Toolbar toolbar;
    private ProgressBar progress;
    private View error;
    private int selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        init();
        createRecycler();
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            selectedCategory = savedInstanceState.getInt(SELECTED_CATEGORY);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_CATEGORY, selectedCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        createSpinner(menu);
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

    private void init() {
        newsDatabase = NewsDatabase.getAppDatabase(this);

        error = findViewById(R.id.error_layout);
        progress = findViewById(R.id.progress_bar);
        newsRecycler = findViewById(R.id.news_recycler_view);
        toolbar = findViewById(R.id.toolbar);

        FloatingActionButton loadNewsFab = findViewById(R.id.load_news_fab);
        loadNewsFab.setOnClickListener(loadNews);

        Button retryButton = findViewById(R.id.retry_button);
        retryButton.setOnClickListener(loadNews);
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

    private void createSpinner(Menu menu) {
        MenuItem categorySpinner = menu.findItem(R.id.category_spinner);
        Spinner spinner = (Spinner) categorySpinner.getActionView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.categoty_spinner_item, Const.CATEGORY_LIST);
        spinner.setAdapter(adapter);
        spinner.setSelection(selectedCategory);
        spinner.setOnItemSelectedListener(changeCategoryListener);
    }

    private void loadItemsFromNetwork() {
        showProgress();
        endPoint = RestApi.getInstance().getEndPoint();
        compositeDisposable.add(
                endPoint.getNews()
                        .map(newsResponse -> {
                            List<NewsEntity> result = NewsTypeConverter.convertToDatabase(newsResponse.getResults());
                            newsDatabase.getNewsDao().deleteAll();
                            newsDatabase.getNewsDao().insertAll(result);
                            return result;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateItems, this::handleError)
        );
    }

    private void loadItemsFromNetworkByCategory(String category) {
        showProgress();
        endPoint = RestApi.getInstance().getEndPoint();
        compositeDisposable.add(
                endPoint.getNewsByCategory(category)
                        .map(newsResponse -> NewsTypeConverter.convertToDatabase(newsResponse.getResults()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateItems, this::handleError)
        );
    }

    private void loadItemsFromDB() {
        compositeDisposable.add(
                newsDatabase.getNewsDao().getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateItems, this::handleError)
        );
    }

    private void loadItemsFromDbByCategory(String category) {
        compositeDisposable.add(
                newsDatabase.getNewsDao().getNewsByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateItems, this::handleError)
        );
    }

    private void updateItems(List<NewsEntity> newsList) {
        if (newsAdapter != null) {
            newsAdapter.replaceItems(newsList);
        }
        Utils.setVisible(newsRecycler, true);
        Utils.setVisible(progress, false);
        Utils.setVisible(error, false);
    }

    private void showProgress() {
        Utils.setVisible(progress, true);
        Utils.setVisible(error, false);
        Utils.setVisible(newsRecycler, false);
    }

    private void handleError(Throwable th) {
        Log.e(Const.LOG_TAG, th.getMessage(), th);
        Utils.setVisible(error, true);
        Utils.setVisible(progress, false);
        Utils.setVisible(newsRecycler, false);
    }

    private View.OnClickListener loadNews = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (selectedCategory == 0) {
                NewsListActivity.this.loadItemsFromNetwork();
            } else {
                NewsListActivity.this.loadItemsFromNetworkByCategory(Const.CATEGORY_LIST[selectedCategory]
                        .toLowerCase().replaceAll("\\s", ""));
            }
        }
    };

    private AdapterView.OnItemSelectedListener changeCategoryListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedCategory = position;
            String category = Const.CATEGORY_LIST[position].toLowerCase()
                    .replaceAll("\\s", "");
            if (selectedCategory != 0) {
                loadItemsFromDbByCategory(category);
            } else {
                loadItemsFromDB();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
