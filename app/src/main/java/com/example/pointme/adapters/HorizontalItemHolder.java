package com.example.pointme.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pointme.R;

public class HorizontalItemHolder extends RecyclerView.ViewHolder {

    public final TextView dateTextview;
    public final TextView timeTextview;

    public HorizontalItemHolder(View itemView) {
        super(itemView);
        dateTextview = itemView.findViewById(R.id.workshop_date);
        timeTextview = itemView.findViewById(R.id.workshop_time);
    }
}