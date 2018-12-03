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
import com.example.indus.nytimes.utils.Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements INewsClickListener {

    private static final String SELECTED_CATEGORY = "selected_category";
    private static final String CURRENT_FRAGMENT = "current_fragment";

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

            if (!isDetails) {
                //createFragment(newsListFragment);
            }

            newsListFragment.setCategory(selectedCategory);
        } else {
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
                break;
            case R.id.delete_menu_button:
                newsDetailsFragment.deleteFromDB();
                closeFragment(newsDetailsFragment);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setVisibleMenuItem(false);
    }

    private void init() {
        newsListFragment = new NewsListFragment();
        newsDetailsFragment = new NewsDetailsFragment();
        toolbar = findViewById(R.id.toolbar);
        selectedCategory = 0;

    }

    private void createFragment(Fragment fragment) {
        Utils.log("*** MAIN ACTIVITY *** CreateFragment");
        isDetails = fragment instanceof NewsDetailsFragment;
        setVisibleMenuItem(isDetails);
        fragmentManager = getSupportFragmentManager();
        if (isDetails) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack("ny_times")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void closeFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .remove(fragment)
                .commit();
        fragmentManager.popBackStack();
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
            Utils.log("*** MAIN ACTIVITY *** OnItemClick");
            selectedCategory = position;
            newsListFragment.setCategory(selectedCategory);
            if (selectedCategory != 0) {
                newsListFragment.loadItemsFromDbByCategory(Utils.getCategoryById(position));
            } else {
                newsListFragment.loadItemsFromDB();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onNewsClick(String url, int id) {
        NewsDetailsFragment.setArguments(newsDetailsFragment, url, id);
        createFragment(newsDetailsFragment);
    }

    public void setVisibleMenuItem(boolean isDetails) {
        if (categorySpinner != null && delete_button != null) {
            categorySpinner.setVisible(!isDetails);
            delete_button.setVisible(isDetails);
        }
    }
}
