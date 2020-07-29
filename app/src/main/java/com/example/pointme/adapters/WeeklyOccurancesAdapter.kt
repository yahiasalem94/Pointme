package com.example.pointme.adapters



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pointme.R
import kotlinx.android.synthetic.main.weekly_occurances_listview.view.*

class WeeklyOccurancesAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.weekly_occurances_listview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.daysName.text = items.get(position)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val daysName = view.dayName
}