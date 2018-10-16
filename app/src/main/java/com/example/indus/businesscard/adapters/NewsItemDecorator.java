package com.example.indus.businesscard.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class NewsItemDecorator extends RecyclerView.ItemDecoration {
    private int space;

    public NewsItemDecorator(int space)
    {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
             outRect.bottom = space;
    }
}
