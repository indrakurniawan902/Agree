package com.agree.ecosystem.utilities.domain.reqres.model.subsector

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SubSector(
    val icon: String = "",
    val id: String = "",
    val name: String = "",
    val sectorId: Int = 0,
    val sectorName: String = "",
    var commodities: List<Commodity> = emptyList(),
    val status: String = "",
    val fieldAssistantId: String = "",
    val fieldAssistantName: String = "",
    val description: String = "",
    val scheduleStatus: String = "",
    val scheduleDescription: String = ""
) : Parcelable {
    fun getFullSectorName() = "$sectorName $name"
}
