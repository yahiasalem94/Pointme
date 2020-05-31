package com.example.pointme.adapters;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pointme.R;
import com.google.android.material.button.MaterialButton;

public class ReviewsItemHolder extends RecyclerView.ViewHolder {

    public TextView authorName;
    public TextView date;
    public RatingBar ratingBar;
    public TextView reviewText;
    public CardView cardView;

    public ReviewsItemHolder(View itemView) {
        super(itemView);
        authorName = itemView.findViewById(R.id.authorName);
        date = itemView.findViewById(R.id.date_of_review);
        ratingBar = itemView.findViewById(R.id.ratingBar);
        reviewText = itemView.findViewById(R.id.reviewText);
        cardView = itemView.findViewById(R.id.card_view);
    }
}