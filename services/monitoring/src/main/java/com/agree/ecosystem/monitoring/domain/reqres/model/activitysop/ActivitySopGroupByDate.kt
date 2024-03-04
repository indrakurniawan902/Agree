package com.agree.ecosystem.monitoring.domain.reqres.model.activitysop

import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTC2TimeTo

data class ActivitySopGroupByDate(
    val date: String,
    val activitySop: List<ActivitySop>
) {
    fun getSimpleDayDate(): String = date.convertUTC2TimeTo(ConverterDate.SIMPLE_DAY_DATE)
}
