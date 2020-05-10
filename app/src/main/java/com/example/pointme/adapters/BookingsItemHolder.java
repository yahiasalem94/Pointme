package com.example.pointme.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pointme.R;
import com.google.android.material.button.MaterialButton;

public class BookingsItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView meetingName;
    public TextView spName;
    public TextView date;
    public TextView time;
    public TextView approved;
    public MaterialButton reviewButton;

    private final BookingsAdapter.BookingsAdapterOnClickHandler mClickHandler;

    public BookingsItemHolder(View itemView, BookingsAdapter.BookingsAdapterOnClickHandler mClickHandler) {
        super(itemView);
        meetingName = itemView.findViewById(R.id.meetingName);
        spName = itemView.findViewById(R.id.spName);
        date = itemView.findViewById(R.id.date);
        time = itemView.findViewById(R.id.time);
        approved = itemView.findViewById(R.id.approved);
        reviewButton = itemView.findViewById(R.id.reviewNow);
        this.mClickHandler = mClickHandler;
        reviewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mClickHandler.onClick(getAdapterPosition());
    }
}