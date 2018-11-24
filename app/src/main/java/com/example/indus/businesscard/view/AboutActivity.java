package com.example.indus.businesscard.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.indus.businesscard.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MY_MAIL = "buvaka.sergey89@gmail.com";
    private static final String MY_WEB = "https://github.com/industradamus";
    private static final String MY_NUMBER = "+79689613600";

    private RelativeLayout phoneLayout, mailLayout, webLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
        setClickListeners();
        createToolbar();
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
                callMe();
                break;

            case R.id.mail_layout:
                sendEmail();
                break;

            case R.id.web_layout:
                openResume();
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

    private void callMe() {
        String uriTel = "tel:" + MY_NUMBER;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uriTel));
        startActivity(intent);
    }

    private void openResume() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MY_WEB));
        startActivity(intent);
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{MY_MAIL});
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }
}
