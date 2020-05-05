package com.example.pointme.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pointme.R;

public class TimeAdapterItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTimeTextView;
        public ImageView mCheckImageView;
        private final TimeAdapter.TimeAdapterOnClickHandler mClickHandler;

        public TimeAdapterItemHolder(View itemView, TimeAdapter.TimeAdapterOnClickHandler mClickHandler) {
            super(itemView);
            mTimeTextView = itemView.findViewById(R.id.time_text_view);
            mCheckImageView = itemView.findViewById(R.id.check_image_view);
            this.mClickHandler = mClickHandler;
            itemView.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        mClickHandler.onClick(getAdapterPosition());
    }
}
