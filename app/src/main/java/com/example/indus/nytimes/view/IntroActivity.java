package com.example.indus.nytimes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.indus.nytimes.R;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IntroActivity extends AppCompatActivity {

    private static final String NEED_TO_SHOW_INTRO = "show_intro";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SharedPreferences sharedPreferences;
    private boolean needToShowIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        needToShowIntro = loadPreference();
        if (needToShowIntro) {
            setContentView(R.layout.activity_intro);
            needToShowIntro = false;
            savePreference();
            Disposable disposable = Completable.complete().delay(3, TimeUnit.SECONDS).subscribe(this::startSecondActivity);
            compositeDisposable.add(disposable);
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
        startActivity(new Intent(this, NewsListFragment.class));
        finish();
    }

    private void savePreference(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NEED_TO_SHOW_INTRO, needToShowIntro);
        editor.apply();
    }

    private boolean loadPreference(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        return sharedPreferences.getBoolean(NEED_TO_SHOW_INTRO, true);
    }
}
