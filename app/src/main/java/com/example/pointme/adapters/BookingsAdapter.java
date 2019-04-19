package com.example.pointme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.models.Booking;
import com.example.pointme.models.ProfileInfo;
import com.example.pointme.models.ProvidersInfo;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsItemHolder> {

    private List<Booking> itemList;
    private RecyclerViewClickListener mRecyclerViewListener;

    public BookingsAdapter(List<Booking> itemList, RecyclerViewClickListener callback) {
        this.itemList = itemList;
        mRecyclerViewListener = callback;
    }

    public void newList(List<Booking> itemList) {
        if (this.itemList != null) {
            this.itemList.clear();
        }
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(itemList!=null)
        {
            ret = itemList.size();
        }
        return ret;
    }

    @Override
    public void onBindViewHolder(BookingsItemHolder contactViewHolder, int i) {
        Booking info = itemList.get(i);
        contactViewHolder.getNameText().setText(info.getEvId());
    }

    @Override
    public BookingsItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.bookings_card_layout, viewGroup, false);

        final TextView titleView = itemView.findViewById(R.id.txtName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewListener.onClick(titleView.getText().toString());
            }
        });
        return new BookingsItemHolder(itemView);
    }
}