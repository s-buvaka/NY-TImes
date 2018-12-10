package com.example.indus.nytimes.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpDelegate;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.indus.nytimes.R;
import com.example.indus.nytimes.mvp.AboutPresenter;
import com.example.indus.nytimes.mvp.AboutView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener, AboutView {
    private MvpDelegate<AboutActivity> mMvpDelegate;

    private RelativeLayout phoneLayout, mailLayout, webLayout;

    @InjectPresenter
    AboutPresenter aboutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getMvpDelegate().onCreate(savedInstanceState);

        initView();
        setClickListeners();
        createToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getMvpDelegate().onAttach();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getMvpDelegate().onAttach();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    protected void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getMvpDelegate().onDestroyView();

        if (isFinishing()) {
            getMvpDelegate().onDestroy();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.phone_layout:
                aboutPresenter.callMe(this);
                break;

            case R.id.mail_layout:
                aboutPresenter.sendEmail(this);
                break;

            case R.id.web_layout:
                aboutPresenter.openResume(this);
                break;
        }
    }

    private void initView() {
        phoneLayout = findViewById(R.id.phone_layout);
        mailLayout = findViewById(R.id.mail_layout);
        webLayout = findViewById(R.id.web_layout);

    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setClickListeners() {
        phoneLayout.setOnClickListener(this);
        mailLayout.setOnClickListener(this);
        webLayout.setOnClickListener(this);

    }

    @Override
    public void callPhone(@NonNull String number) {

    }

    @Override
    public void sendMail(@NonNull String email) {

    }

    @Override
    public void openWeb(@NonNull String link) {

    }

    public MvpDelegate<AboutActivity> getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }
        return mMvpDelegate;
    }
}
