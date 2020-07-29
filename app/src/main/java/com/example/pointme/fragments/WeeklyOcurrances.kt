package com.example.pointme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pointme.R
import com.example.pointme.adapters.WeeklyOccurancesAdapter
import kotlinx.android.synthetic.main.fragment_weekly_ocurrances.*
import java.text.DateFormatSymbols

/**
 * A simple [Fragment] subclass.
 */
class WeeklyOcurrances : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dfs: DateFormatSymbols

    val days: ArrayList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        addDays()
        linearLayoutManager = LinearLayoutManager(activity)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_ocurrances, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weeklyDays.layoutManager = linearLayoutManager
        weeklyDays.adapter = WeeklyOccurancesAdapter(days, this.requireActivity())
    }
    private fun addDays() {
        dfs = DateFormatSymbols()
        val result = dfs.weekdays
        val weekdays = result.toMutableList()
        weekdays.removeAt(0)
        for (weekday in weekdays) {
            days.add(weekday)
        }
    }

}
