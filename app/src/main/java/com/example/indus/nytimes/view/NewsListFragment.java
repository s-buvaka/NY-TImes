package com.example.indus.nytimes.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListFragment extends Fragment {
    private static final int SPAN_COUNT = 2;
    private static final int SPACE_ITEM_DECORATION = 4;

    private NewsAdapter newsAdapter;
    private CompositeDisposable compositeDisposable;
    private INewsEndPoint endPoint;
    private NewsDatabase newsDatabase;
    private RecyclerView newsRecycler;
    private ProgressBar progress;
    private View error;
    private int selectedCategory;
    private IMenuVisibilityController visibilityController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (visibilityController != null) visibilityController.setVisibleMenuItem(false);
        if (selectedCategory != 0) {
            loadItemsFromDbByCategory(Utils.getCategoryById(selectedCategory));
        } else {
            loadItemsFromDB();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        init(view);
        createRecycler();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    void registerCallback (Activity activity){
        visibilityController = (IMenuVisibilityController) activity;
    }

    void loadItemsFromDB() {
        compositeDisposable.add(
                newsDatabase.getNewsDao().getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateItems, this::handleError)
        );
    }

    void loadItemsFromDbByCategory(String category) {
        compositeDisposable.add(
                newsDatabase.getNewsDao().getNewsByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateItems, this::handleError)
        );
    }

    void setCategory(int category) {
        selectedCategory = category;
    }

    private void init(View view) {
        newsDatabase = NewsDatabase.getAppDatabase(getActivity());
        compositeDisposable = new CompositeDisposable();

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
                NewsListFragment.this.loadItemsFromNetwork();
            } else {
                NewsListFragment.this.loadItemsFromNetworkByCategory(Const.CATEGORY_LIST[selectedCategory]
                        .toLowerCase().replaceAll("\\s", ""));
            }
        }
    };
}
