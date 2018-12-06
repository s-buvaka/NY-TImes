package com.example.indus.nytimes.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.indus.nytimes.R;
import com.example.indus.nytimes.utils.Const;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements INewsClickListener {

    private static final String SELECTED_CATEGORY = "selected_category";
    private static final String CURRENT_FRAGMENT = "current_fragment";
    private static final String DETAILS_FRAGMENT_TAG = "details_fragment";
    private static final String NEWS_LIST_FRAGMENT_TAG = "news_list_fragment";
    private static final String BACK_STACK_NAME = "ny_times_back_stack";

    private NewsListFragment newsListFragment;
    private NewsDetailsFragment newsDetailsFragment;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private MenuItem delete_button;
    private MenuItem categorySpinner;
    private int selectedCategory;
    private boolean isDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            isDetails = savedInstanceState.getBoolean(CURRENT_FRAGMENT);
            selectedCategory = savedInstanceState.getInt(SELECTED_CATEGORY);
            newsListFragment = (NewsListFragment) fragmentManager.findFragmentByTag(NEWS_LIST_FRAGMENT_TAG);

            //NewsListFragment.setCategory(this, selectedCategory);
            setVisibleMenuItem(isDetails);
        } else {
            newsListFragment = new NewsListFragment();
            createFragment(newsListFragment);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_CATEGORY, selectedCategory);
        outState.putBoolean(CURRENT_FRAGMENT, isDetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_news_menu_list, menu);
        delete_button = menu.findItem(R.id.delete_menu_button);
        createSpinner(menu);
        setVisibleMenuItem(isDetails);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_me_menu_button:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case android.R.id.home:
                super.onBackPressed();
                setVisibleMenuItem(!isDetails);
                stepBack();
                break;
            case R.id.delete_menu_button:
                newsDetailsFragment = (NewsDetailsFragment) fragmentManager.findFragmentByTag(DETAILS_FRAGMENT_TAG);
                if (newsDetailsFragment != null) {
                    newsDetailsFragment.deleteFromDB();
                    closeFragment(newsDetailsFragment);
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stepBack();
    }

    @Override
    public void onNewsClick(String url, int id) {
        NewsDetailsFragment.setArguments(newsDetailsFragment, url, id);
        createFragment(newsDetailsFragment);
    }

    private void init() {
        newsDetailsFragment = new NewsDetailsFragment();
        fragmentManager = getSupportFragmentManager();
        toolbar = findViewById(R.id.toolbar);
        selectedCategory = 0;
    }

    private void createFragment(Fragment fragment) {
        isDetails = fragment instanceof NewsDetailsFragment;
        setVisibleMenuItem(isDetails);

        if (isDetails) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, DETAILS_FRAGMENT_TAG)
                    .addToBackStack(BACK_STACK_NAME)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, NEWS_LIST_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void closeFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .remove(fragment)
                .commit();
        isDetails = false;
        setVisibleMenuItem(false);
        fragmentManager.popBackStack();
        NewsListFragment.setCategory(this, selectedCategory);
    }

    private void createSpinner(Menu menu) {
        categorySpinner = menu.findItem(R.id.category_spinner);
        Spinner spinner = (Spinner) categorySpinner.getActionView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.categoty_spinner_item, Const.CATEGORY_LIST);
        spinner.setAdapter(adapter);
        spinner.setSelection(selectedCategory);
        spinner.setOnItemSelectedListener(changeCategoryListener);
    }

    private AdapterView.OnItemSelectedListener changeCategoryListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            newsListFragment = (NewsListFragment) fragmentManager.findFragmentByTag(NEWS_LIST_FRAGMENT_TAG);
            selectedCategory = position;
            NewsListFragment.setCategory(view.getContext(), position);
            //newsListFragment.loadItemsFromDb(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void setVisibleMenuItem(boolean isVisible) {
        if (categorySpinner != null && delete_button != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(isVisible);
            categorySpinner.setVisible(!isVisible);
            delete_button.setVisible(isVisible);
        }
    }

    private void stepBack() {
        setVisibleMenuItem(!isDetails);
        if (isDetails) {
            isDetails = false;
            newsListFragment = (NewsListFragment) fragmentManager.findFragmentByTag(NEWS_LIST_FRAGMENT_TAG);
            if (newsListFragment != null) {
                newsListFragment.loadItemsFromDb(selectedCategory);
            }
        }
    }
}
