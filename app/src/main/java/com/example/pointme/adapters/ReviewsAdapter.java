package com.example.pointme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pointme.R;
import com.example.pointme.models.Reviews;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsItemHolder>  {

    private List<Reviews> reviewsList = new ArrayList<>();

    private NestedScrollView scrollView;
    private int minHeight;
    private static int currentPosition = 0;
    private Context context;

//    private ProfileAdapterOnClickHandler mClickHandler;



//    public interface ReviewsAdapterOnClickHandler {
//        void onClick(@Type int type, int position);
//    }

    public ReviewsAdapter() {
//        this.mClickHandler = mClickHandler;
//        this.context = context;
//        this.scrollView = scrollView;
    }

    public void setReviews(List<Reviews> reviewsList) {
        if(this.reviewsList != null)
            this.reviewsList.clear();

        this.reviewsList.addAll(reviewsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        /* TODO Fix null pointer */
        if (reviewsList == null) return 0;
        return (reviewsList.size());
    }

    @Override
    public ReviewsItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.review_card_layout, viewGroup, false);

        return new ReviewsItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ReviewsItemHolder holder, final int position) {
        holder.authorName.setText(reviewsList.get(position).getCrName());
        holder.date.setText(reviewsList.get(position).getCreated().toString());
        holder.reviewText.setText(reviewsList.get(position).getReviewText());
        holder.ratingBar.setRating(reviewsList.get(position).getAvgRating());
    }
}