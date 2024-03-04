package com.agree.ecosystem.monitoring.data.reqres.model.eventdotcalendar

import com.agree.ecosystem.monitoring.domain.reqres.model.eventdotcalendar.EventDotCalendar
import com.google.gson.annotations.SerializedName

data class EventDotCalendarItem(
    @field:SerializedName("is_completed")
    val isCompleted: Boolean? = null,

    @field:SerializedName("date")
    val date: String? = null
) {
    fun toEventDotCalendar(): EventDotCalendar {
        return EventDotCalendar(
            isCompleted = isCompleted ?: false,
            date = date.orEmpty()
        )
    }
}
