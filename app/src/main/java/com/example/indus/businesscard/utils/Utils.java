package com.example.indus.businesscard.utils;

import android.view.View;

public class Utils {

    public static void setVisible(View view, boolean show) {
        if (view == null) return;

        int visibility = show ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }
}
