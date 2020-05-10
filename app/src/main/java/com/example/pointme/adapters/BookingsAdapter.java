package com.example.pointme.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pointme.interfaces.RecyclerViewClickListener;
import com.example.pointme.R;
import com.example.pointme.models.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsItemHolder> {

    private List<Booking> itemList = new ArrayList<>();
    private BookingsAdapterOnClickHandler mClickHandler;
    private boolean isPastBookings = false;

    public interface BookingsAdapterOnClickHandler {
        void onClick(int position);
    }

    public BookingsAdapter(BookingsAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public void newList(List<Booking> itemList, boolean isPastBookings) {
        if (this.itemList != null)
            this.itemList.clear();

        this.itemList.addAll(itemList);
        this.isPastBookings = isPastBookings;
        notifyDataSetChanged();
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
    public BookingsItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.bookings_card_layout, viewGroup, false);

        return new BookingsItemHolder(itemView, mClickHandler);
    }

    @Override
    public void onBindViewHolder(BookingsItemHolder contactViewHolder, int i) {
        Booking info = itemList.get(i);
        contactViewHolder.meetingName.setText(info.getMeetingName());
        contactViewHolder.spName.setText("By " + info.getSpName());
        contactViewHolder.date.setText(info.getBookingDetails().get(0).getDate());
        contactViewHolder.time.setText(info.getBookingDetails().get(0).getTime());
        contactViewHolder.approved.setText(info.getApprovalStatus());
        if(isPastBookings) {
            contactViewHolder.reviewButton.setVisibility(View.VISIBLE);
        } else {
            contactViewHolder.reviewButton.setVisibility(View.GONE);
        }

    }
}