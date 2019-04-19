package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.pointme.R;

public class BookingsItemHolder extends RecyclerView.ViewHolder {

    private TextView name;

    public BookingsItemHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            name = itemView.findViewById(R.id.bookingName);
        }
    }

    public TextView getNameText() {
        return name;
    }

}