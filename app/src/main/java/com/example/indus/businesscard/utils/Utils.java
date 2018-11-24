package com.example.indus.businesscard.utils;

import android.util.Log;
import android.view.View;

public class Utils {

    public static void log(String message) {
        Log.d(Const.LOG_TAG, message);
    }

    public static void setVisible(View view, boolean show) {
        if (view == null) return;

        int visibility = show ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }
}
