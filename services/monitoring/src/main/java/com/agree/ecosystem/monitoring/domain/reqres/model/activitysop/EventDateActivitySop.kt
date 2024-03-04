package com.agree.ecosystem.monitoring.domain.reqres.model.activitysop

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Keep
@Parcelize
data class EventDateActivitySop(
    val date: String,
    val isAllCompleted: Boolean
) : Parcelable {
    fun getDateBySqlDate(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("id"))
        return formatter.format(parser.parse(date))
    }
}
