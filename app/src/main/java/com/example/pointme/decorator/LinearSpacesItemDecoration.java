package com.example.pointme.decorator;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class LinearSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public LinearSpacesItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
       super.getItemOffsets(outRect, view, parent, state);
       outRect.bottom = spacing;
    }
}
