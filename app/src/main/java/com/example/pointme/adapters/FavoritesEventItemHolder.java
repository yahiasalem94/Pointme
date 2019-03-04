package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;

public class FavoritesEventItemHolder extends RecyclerView.ViewHolder {

    private TextView title = null;
    private TextView name;

    public FavoritesEventItemHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            name = itemView.findViewById(R.id.name);
            title = itemView.findViewById(R.id.title);
        }
    }

    public TextView geNameText() {
        return name;
    }

    public TextView getTitleText() {
        return title;
    }
}