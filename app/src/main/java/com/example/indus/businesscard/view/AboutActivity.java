package com.example.indus.businesscard.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.indus.businesscard.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MY_MAIL = "buvaka.sergey89@gmail.com";
    private static final String MY_WEB = "https://hh.ru/applicant/resumes/view?resume=38b9e174ff05bed6f30039ed1f634e4b473539";
    private static final String MY_NUMBER = "89689613600";
    private static final String MY_INSTAGRAM = "https://www.instagram.com/neformalniy_papasha/";
    private static final String MY_FACEBOOK = "https://www.facebook.com/profile.php?id=100010244763199";

    private TextView myPhoneNumber;
    private EditText messageEnter;
    private Button sendMessageBtn;
    private ImageView myResumeWebLink;
    private ImageView instagramLogo, whatsAppLogo, facebookLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
        viewSetFocusable();
        setClickListeners();
        addDisclaimer();
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
            case R.id.facebook_logo:
                openFacebook();
                break;
            case R.id.instagram_logo:
                openInstagram();
                break;
            case R.id.my_phone_number:
                callMe();
                break;
            case R.id.my_web_link:
                openResume();
                break;
            case R.id.send_message_btn:
                sendEmail();
                break;
            case R.id.whatsapp_logo:
                openWhatsApp();
                break;
        }
    }

    private void initView() {
        myPhoneNumber = findViewById(R.id.my_phone_number);
        myResumeWebLink = findViewById(R.id.my_web_link);
        messageEnter = findViewById(R.id.message_enter);
        sendMessageBtn = findViewById(R.id.send_message_btn);
        instagramLogo = findViewById(R.id.instagram_logo);
        whatsAppLogo = findViewById(R.id.whatsapp_logo);
        facebookLogo = findViewById(R.id.facebook_logo);
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void viewSetFocusable() {
        myPhoneNumber.setFocusable(true);
        myResumeWebLink.setFocusable(true);
        instagramLogo.setFocusable(true);
        whatsAppLogo.setFocusable(true);
        facebookLogo.setFocusable(true);


    }

    private void setClickListeners() {
        myPhoneNumber.setOnClickListener(this);
        myResumeWebLink.setOnClickListener(this);
        sendMessageBtn.setOnClickListener(this);
        instagramLogo.setOnClickListener(this);
        whatsAppLogo.setOnClickListener(this);
        facebookLogo.setOnClickListener(this);
    }

    private void openFacebook() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MY_FACEBOOK));
        startActivity(intent);
    }

    private void openInstagram() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MY_INSTAGRAM));
        startActivity(intent);
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
        String message = messageEnter.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{MY_MAIL});
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }

    @SuppressLint("IntentReset")
    private void openWhatsApp() {
        String uriSMS = "smsto:" + MY_NUMBER;
        Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse(uriSMS));
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    private void addDisclaimer() {
        LinearLayout linearLayout = findViewById(R.id.scroll_layout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,8,8,8);
        TextView textView = new TextView(this);
        textView.setText(R.string.disclaimer);
        textView.setGravity(Gravity.END);
        textView.setLayoutParams(layoutParams);
        linearLayout.addView(textView);
    }
}
