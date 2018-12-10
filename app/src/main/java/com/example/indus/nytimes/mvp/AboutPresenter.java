package com.example.indus.nytimes.mvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.example.indus.nytimes.ui.BasePresenter;

@InjectViewState
public class AboutPresenter extends BasePresenter<AboutView> {

    private static final String MY_MAIL = "buvaka.sergey89@gmail.com";
    private static final String MY_WEB = "https://github.com/industradamus";
    private static final String MY_NUMBER = "+79689613600";

    public void sendEmail(Context context) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{MY_MAIL});
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }

    public void openResume(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MY_WEB));
        context.startActivity(intent);
    }

    public void callMe(Context context) {
        String uriTel = "tel:" + MY_NUMBER;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uriTel));
        context.startActivity(intent);
    }
}
