package com.example.pointme.decorator

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.LocalDate

class ProvideDayEnableDecorator(days: List<LocalDate>): DayViewDecorator {
    private var days: List<LocalDate>? = null

    init {
        this.days = days
    }


    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day!!.date in days!!

    }

    override fun decorate(view: DayViewFacade?) {
        view!!.setDaysDisabled(false)
    }

}