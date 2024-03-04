package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.calendar

import android.view.View
import com.agree.ecosystem.monitoring.databinding.ItemCalendarDayBinding
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(
    view: View
) : ViewContainer(view) {
    val binding: ItemCalendarDayBinding by lazy {
        ItemCalendarDayBinding.bind(view)
    }
}
