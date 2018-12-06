package com.example.indus.nytimes.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.indus.nytimes.R;
import com.example.indus.nytimes.adapters.NewsAdapter;
import com.example.indus.nytimes.adapters.NewsItemDecorator;
import com.example.indus.nytimes.database.NewsDatabase;
import com.example.indus.nytimes.database.NewsTypeConverter;
import com.example.indus.nytimes.modeldto.NewsEntity;
import com.example.indus.nytimes.network.INewsEndPoint;
import com.example.indus.nytimes.network.RestApi;
import com.example.indus.nytimes.utils.Const;
import com.example.indus.nytimes.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListFragment extends Fragment {
    private static final int SPAN_COUNT = 2;
    private static final int SPACE_ITEM_DECORATION = 4;
    private static final String SELECTED_CATEGORY = "selected_category";
    private static final String CATEGORY_INTENT_ACTION = "com.example.indus.category_intent";

    private NewsAdapter newsAdapter;
    private CompositeDisposable compositeDisposable;
    private NewsDatabase newsDatabase;
    private BroadcastReceiver receiver;
    private RecyclerView newsRecycler;
    private ProgressBar progress;
    private View error;
    private int selectedCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);

        init(view);
        registerCategoryReceiver();

        if (savedInstanceState != null) {
            selectedCategory = savedInstanceState.getInt(SELECTED_CATEGORY);
        }
        createRecycler();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadItemsFromDb(selectedCategory);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_CATEGORY, selectedCategory);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    void loadItemsFromDb(int categoryId) {
        String category = Const.CATEGORY_LIST[categoryId];
        Single<List<NewsEntity>> newsData;

        if (categoryId == 0) {
            newsData = newsDatabase.getNewsDao().getAll()
                    .subscribeOn(Schedulers.io());
        } else {
            newsData = newsDatabase.getNewsDao().getNewsByCategory(category)
                    .subscribeOn(Schedulers.io());
        }

        compositeDisposable.add(
                newsData.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateItems, this::handleError));
    }

    private void loadItemsFromNetwork(int categoryId) {
        String category = Const.CATEGORY_LIST[categoryId]
                .toLowerCase().replaceAll("\\s", "");
        Single<List<NewsEntity>> newsData;

        showProgress();
        INewsEndPoint endPoint = RestApi.getInstance().getEndPoint();

        if (categoryId == 0) {
            newsData = endPoint.getNews()
                    .map(newsResponse -> {
                        List<NewsEntity> result = NewsTypeConverter.convertToDatabase(newsResponse.getResults());
                        newsDatabase.getNewsDao().deleteAll();
                        newsDatabase.getNewsDao().insertAll(result);
                        return result;
                    })
                    .subscribeOn(Schedulers.io());
        } else {
            newsData = endPoint.getNewsByCategory(category)
                    .map(newsResponse -> {
                        List<NewsEntity> result = NewsTypeConverter.convertToDatabase(newsResponse.getResults());
                        newsDatabase.getNewsDao().insertAll(result);
                        return result;
                    })
                    .subscribeOn(Schedulers.io());
        }
        compositeDisposable.add(
                newsData.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateItems, this::handleError)
        );
    }

    private void init(View view) {
        compositeDisposable = new CompositeDisposable();
        newsDatabase = NewsDatabase.getAppDatabase(getActivity());

        error = view.findViewById(R.id.error_layout);
        progress = view.findViewById(R.id.progress_bar);
        newsRecycler = view.findViewById(R.id.news_recycler_view);

        FloatingActionButton loadNewsFab = view.findViewById(R.id.load_news_fab);
        loadNewsFab.setOnClickListener(loadNews);

        Button retryButton = view.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(loadNews);
    }

    private void createRecycler() {
        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getActivity());
        } else {
            layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        }
        newsRecycler.addItemDecoration(new NewsItemDecorator(Objects.requireNonNull(getContext()), SPACE_ITEM_DECORATION));
        newsRecycler.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(getActivity());
        newsRecycler.setAdapter(newsAdapter);
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
        Utils.log(th.getMessage() + th);
        Utils.setVisible(error, true);
        Utils.setVisible(progress, false);
        Utils.setVisible(newsRecycler, false);
    }

    private View.OnClickListener loadNews = v -> NewsListFragment.this.loadItemsFromNetwork(selectedCategory);

    private void registerCategoryReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                selectedCategory = intent.getIntExtra(SELECTED_CATEGORY, -1);
                loadItemsFromDb(selectedCategory);
            }
        };
        IntentFilter intentFilter = new IntentFilter(CATEGORY_INTENT_ACTION);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, intentFilter);
    }

    static void setCategory(Context context, int category) {
        Intent intent = new Intent();
        intent.setAction(CATEGORY_INTENT_ACTION);
        intent.putExtra(SELECTED_CATEGORY, category);
        context.sendBroadcast(intent);
    }
}
