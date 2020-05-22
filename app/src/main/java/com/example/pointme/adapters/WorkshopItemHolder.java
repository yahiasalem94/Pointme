package com.example.pointme.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;

public class WorkshopItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final RecyclerView recyclerView;
    private final WorkshopAdapter.WorkshopAdapterOnClickHandler mClickHandler;

    public WorkshopItemHolder(View itemView, WorkshopAdapter.WorkshopAdapterOnClickHandler mClickHandler) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.workshop_horizontal_view);
//        dateTextview = itemView.findViewById(R.id.workshop_date);
//        timeTextview = itemView.findViewById(R.id.workshop_time);
        this.mClickHandler = mClickHandler;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mClickHandler.onClick(getAdapterPosition());
    }

}