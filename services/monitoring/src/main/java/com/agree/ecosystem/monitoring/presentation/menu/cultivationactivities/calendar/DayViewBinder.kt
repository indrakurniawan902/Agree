package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import androidx.core.content.ContextCompat
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.domain.reqres.model.eventdotcalendar.EventDotCalendar
import com.agree.ui.UiKitAttrs
import com.devbase.utils.util.getColorResource
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.oratakashi.viewbinding.core.tools.invisible
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.utility.extension.resolveColor
import java.time.LocalDate

class DayViewBinder(
    private val context: Context,
    private val eventsDate: List<EventDotCalendar>,
    private val date: LocalDate,
    private val callback: (CalendarDay) -> Unit
) : DayBinder<DayViewContainer> {

    private var selectedDate: LocalDate = date
    private val listEventDate = eventsDate.groupBy { it.getDateBySqlDate() }

    override fun bind(
        container: DayViewContainer,
        day: CalendarDay
    ) {
        val tvCalendarDay = container.binding.tvCalendarDay
        val eventDotCalendar = container.binding.imgEventDotCalendar
        tvCalendarDay.text = day.date.dayOfMonth.toString()

        if (listEventDate.keys.contains(day.date.toString())) {
            eventDotCalendar.visible()
            if (day.date <= LocalDate.now()) {
                tvCalendarDay.setBackgroundResource(R.drawable.bg_current_date)

                if (listEventDate[day.date.toString()]?.get(0)?.isCompleted == true) {
                    eventDotCalendar.setColorFilter(
                        context.resolveColor(UiKitAttrs.success_normal) ?: Color.GREEN,
                        PorterDuff.Mode.SRC_IN
                    )
                } else {
                    if (day.date == LocalDate.now())
                        eventDotCalendar.setColorFilter(
                            ContextCompat.getColor(
                                context,
                                com.agree.ui.R.color.tertiary_400
                            ),
                            PorterDuff.Mode.SRC_IN
                        )
                    else
                        eventDotCalendar.setColorFilter(
                            context.resolveColor(UiKitAttrs.error_normal) ?: Color.RED,
                            PorterDuff.Mode.SRC_IN
                        )
                }
            } else {
                eventDotCalendar.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        com.agree.ui.R.color.tertiary_400
                    ),
                    PorterDuff.Mode.SRC_IN
                )
            }
        } else {
            eventDotCalendar.invisible()
        }

        when (day.owner) {
            DayOwner.PREVIOUS_MONTH, DayOwner.NEXT_MONTH -> {
                tvCalendarDay.setTextColor(getColorResource(com.agree.ui.R.color.tertiary_400))
                eventDotCalendar.invisible()
                tvCalendarDay.background = null
            }
            DayOwner.THIS_MONTH -> {
                tvCalendarDay.setTextColor(getColorResource(com.agree.ui.R.color.tertiary_800))
                when (day.date) {
                    selectedDate -> {
                        tvCalendarDay.setBackgroundResource(R.drawable.bg_selection_date)
                    }
                    LocalDate.now() -> {
                        tvCalendarDay.setBackgroundResource(R.drawable.bg_current_date)
                    }
                    else -> {
                        tvCalendarDay.background = null
                    }
                }
            }
        }

        container.binding.root.setOnClickListener {
            selectedDate = day.date
            callback.invoke(day)
        }
    }

    override fun create(view: View): DayViewContainer = DayViewContainer(view)
}
