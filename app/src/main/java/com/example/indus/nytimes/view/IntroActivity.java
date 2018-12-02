package com.example.indus.nytimes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.indus.nytimes.R;

import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import me.relex.circleindicator.CircleIndicator;

public class IntroActivity extends FragmentActivity {

    private static final String NEED_TO_SHOW_INTRO = "show_intro";
    private static final int PAGE_COUNT = 3;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SharedPreferences sharedPreferences;
    private boolean needToShowIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        needToShowIntro = loadPreference();
        if (needToShowIntro) {
            setContentView(R.layout.activity_intro);
            createViewPager();
            needToShowIntro = false;
            savePreference();
            compositeDisposable.add(Completable.complete()
                    .delay(3, TimeUnit.SECONDS)
                    .subscribe(this::startSecondActivity));
        } else {
            needToShowIntro = true;
            savePreference();
            startSecondActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    private void startSecondActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void savePreference() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NEED_TO_SHOW_INTRO, needToShowIntro);
        editor.apply();
    }

    private boolean loadPreference() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        return sharedPreferences.getBoolean(NEED_TO_SHOW_INTRO, true);
    }

    private void createViewPager() {
        ViewPager viewPager = findViewById(R.id.intro_view_pager);
        PagerAdapter pagerAdapter = new IntroFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        CircleIndicator indicator = findViewById(R.id.intro_circle_indicator);
        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class IntroFragmentPagerAdapter extends FragmentPagerAdapter {

        IntroFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return IntroPageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }
}
