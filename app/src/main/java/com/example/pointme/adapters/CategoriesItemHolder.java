package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;

public class CategoriesItemHolder extends RecyclerView.ViewHolder {

        private TextView categoryTitleText = null;
        private ImageView imageView = null;

        public CategoriesItemHolder(View itemView) {
            super(itemView);

            if(itemView != null)
            {
                categoryTitleText = itemView.findViewById(R.id.card_view_image_title);
                imageView = itemView.findViewById(R.id.card_view_image);
            }
        }

        public TextView getCategoryTitleText() {
            return categoryTitleText;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }