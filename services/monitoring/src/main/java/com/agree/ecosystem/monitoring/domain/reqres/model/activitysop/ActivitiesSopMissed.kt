package com.agree.ecosystem.monitoring.domain.reqres.model.activitysop

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.convertUTC2TimeTo
import kotlinx.parcelize.Parcelize

data class ActivitiesSopMissed(
    val date: String,
    val activities: List<ActivitiesMissed>
) {
    fun getSimpleMissedDayDate(): String = date.convertUTC2TimeTo(ConverterDate.SIMPLE_DAY_DATE)
}

@Keep
@Parcelize
data class ActivitiesMissed(
    val date: String,
    val activitiesCompleted: Int,
    val name: String,
    val description: String,
    val detail: String,
    val id: String,
    val phaseName: String,
    val repetition: Int,
    val isCompleted: Boolean,
    val type: String
) : Parcelable {
    fun getDetailActivityMissed(): String = "$name ($activitiesCompleted/$repetition)"
}
