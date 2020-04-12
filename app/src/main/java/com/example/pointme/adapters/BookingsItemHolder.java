package com.example.pointme.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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