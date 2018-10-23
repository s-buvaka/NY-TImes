package com.example.indus.businesscard.adapters;

import android.content.Context;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class NewsItemDecorator extends RecyclerView.ItemDecoration {
    private int space;

    public NewsItemDecorator(Context context, int space)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        this.space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, metrics);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
             outRect.bottom = space;
    }
}
