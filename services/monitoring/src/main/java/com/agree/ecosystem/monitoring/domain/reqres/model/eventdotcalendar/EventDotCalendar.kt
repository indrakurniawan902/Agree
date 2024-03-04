package com.agree.ecosystem.monitoring.domain.reqres.model.eventdotcalendar

import android.os.Parcelable
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTC2TimeTo
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventDotCalendar(
    val isCompleted: Boolean,
    val date: String
) : Parcelable {
    fun getDateBySqlDate(): String {
        return date.convertUTC2TimeTo(ConverterDate.SQL_DATE)
    }
}
