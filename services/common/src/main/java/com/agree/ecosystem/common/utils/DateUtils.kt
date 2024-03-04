package com.agree.ecosystem.common.utils

import android.text.format.DateUtils
import com.agree.ecosystem.core.utils.utility.ConverterDate
import java.text.SimpleDateFormat
import java.util.*

internal fun String.convertTimeToListNotif(desireFormat: ConverterDate): String {
    fun isYesterday(date: Date): Boolean {
        return DateUtils.isToday(date.getTime() + DateUtils.DAY_IN_MILLIS)
    }
    fun isToday(date: Date): Boolean {
        return DateUtils.isToday(date.getTime())
    }
    return try {
        val dateFormat = SimpleDateFormat(ConverterDate.UTC.formatter, Locale("id", "ID"))

        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + (7 * hour))
        dateFormat.applyPattern(desireFormat.formatter)
        var returnValue: String? = null
        if (!isYesterday(newDate) && !isToday(newDate)) returnValue = dateFormat.format(newDate).toString()
        else if (isYesterday(newDate)) returnValue = "Kemarin"
        else if (isToday(newDate)) returnValue = "Hari Ini"
        returnValue.orEmpty()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
