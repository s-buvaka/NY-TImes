package com.example.indus.nytimes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.indus.nytimes.R;
import com.example.indus.nytimes.database.NewsDatabase;
import com.example.indus.nytimes.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsFragment extends Fragment {
    private final static String NEWS_URL = "news_url";
    private final static String NEWS_ID = "news_id";

    private CompositeDisposable compositeDisposable;
    private WebView newsDetails;
    private NewsDatabase newsDatabase;
    private int newsId;
    private String newsUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Utils.log("*** DETAILS FRAGMENT *** OnCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Utils.log("*** DETAILS FRAGMENT *** OnCreateView");
        View view = inflater.inflate(R.layout.news_details_fragment, container, false);

        init(view);
        newsDetails.loadUrl(newsUrl);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Utils.log("*** DETAILS FRAGMENT *** OnDestroyView");
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    void deleteFromDB() {
        compositeDisposable.add(
                Completable.fromAction(() -> newsDatabase.getNewsDao().deleteById(newsId))
                        .subscribeOn(Schedulers.io())
                        .subscribe());
    }

    private void init(View view) {
        compositeDisposable = new CompositeDisposable();
        newsDatabase = NewsDatabase.getAppDatabase(getContext());
        newsDetails = view.findViewById(R.id.news_details);

        Bundle bundle = getArguments();
        if (bundle != null) {
            newsId = bundle.getInt(NEWS_ID);
            newsUrl = bundle.getString(NEWS_URL);

        }
    }

    static void setArguments(@NonNull Fragment fragment, @NonNull String newsUrl, int newsId) {
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_URL, newsUrl);
        bundle.putInt(NEWS_ID, newsId);
        fragment.setArguments(bundle);
    }
}
