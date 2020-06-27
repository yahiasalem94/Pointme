package com.example.pointme.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.provider_calendar_item_view.view.*

class ProviderCalendarItemHolder(view: View): RecyclerView.ViewHolder(view) {
    val mTime = view.time
    val mName = view.name
    val mAddress = view.address_tv
    val status = view.status
}