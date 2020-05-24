package com.example.pointme.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;

public class CategoriesItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView imageView;
//        public final TextView categoryName;
        private final CategoriesAdapter.CategoriesAdapterOnClickHandler mClickHandler;

        public CategoriesItemHolder(View itemView, CategoriesAdapter.CategoriesAdapterOnClickHandler mClickHandler) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_view_image);
//            categoryName = itemView.findViewById(R.id.category_name);
            this.mClickHandler = mClickHandler;
            itemView.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        mClickHandler.onClick(getAdapterPosition());
    }

}