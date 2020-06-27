package com.example.pointme.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointme.R
import com.example.pointme.models.Booking

class ProviderCalendarAdapter(context: Context): RecyclerView.Adapter<ProviderCalendarItemHolder>() {
    private var mBookings: ArrayList<Booking> = ArrayList()
    private var context: Context

    init {
        this.context = context
    }
    fun setBookings(mMeetings: ArrayList<Booking>) {
        mBookings.clear()
        this.mBookings.addAll(mMeetings)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderCalendarItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.provider_calendar_item_view, parent, false)
        return ProviderCalendarItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        /* TODO Fix null pointer */
        return mBookings?.size ?: 0
    }

    override fun onBindViewHolder(holder: ProviderCalendarItemHolder, position: Int) {
        val item: Booking = mBookings!![position]
        /* TODO Fix */
        holder.mTime.text = item.bookingDetails[0].time
        holder.mName.text = item.meetingName
        holder.mAddress.text = item.bookingDetails[0].location.name
        if (item.approvalStatus.equals("Approved")) {
            holder.status.setTextColor(context.getColor(R.color.green))
        } else {
            holder.status.setTextColor(context.getColor(R.color.red))
        }
        holder.status.text = item.approvalStatus
    }
}