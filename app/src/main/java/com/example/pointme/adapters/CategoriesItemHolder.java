package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.Interfaces.RecyclerViewClickListener;
import com.example.pointme.R;

public class CategoriesItemHolder extends RecyclerView.ViewHolder {

        private ImageView imageView = null;

        public CategoriesItemHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_view_image);
        }

        public ImageView getImageView() {
            return imageView;
        }

}