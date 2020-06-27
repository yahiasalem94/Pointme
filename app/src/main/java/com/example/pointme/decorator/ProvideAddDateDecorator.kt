package com.example.pointme.decorator

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.LocalDate

class ProviderAddDateDecorator(): DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return !day!!.isBefore(CalendarDay.today())

    }

    override fun decorate(view: DayViewFacade?) {
        view!!.setDaysDisabled(false)
    }

}