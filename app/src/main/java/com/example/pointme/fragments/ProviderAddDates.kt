package com.example.pointme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pointme.R
import com.example.pointme.decorator.AllDaysDisabledDecorator
import com.example.pointme.decorator.ProviderAddDateDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_MULTIPLE
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_provider_add_dates.*


class ProviderAddDates : Fragment(), OnDateSelectedListener {
    val TAG = ProviderAddDates::class.qualifiedName
    private var numberOfDates: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_add_dates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_dates_calendar.addDecorator(AllDaysDisabledDecorator())
        add_dates_calendar.addDecorator(ProviderAddDateDecorator())
        add_dates_calendar.setOnDateChangedListener(this)
        add_dates_calendar.selectionMode = SELECTION_MODE_MULTIPLE
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
      if (selected) {
          numberOfDates++
          add_time.visibility = View.VISIBLE
      } else {
          numberOfDates--
          if (numberOfDates <= 0) {
              add_time.visibility = View.INVISIBLE
              numberOfDates = 0
          }
      }

    }

}
