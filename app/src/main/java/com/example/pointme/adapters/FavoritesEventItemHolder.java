package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;

public class FavoritesEventItemHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView title;

    public FavoritesEventItemHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            name = itemView.findViewById(R.id.txtName);
            title = itemView.findViewById(R.id.title);
        }
    }

    public TextView getNameText() {
        return name;
    }

}