package com.agree.ecosystem.utilities.data.reqres.model.subsector

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.core.utils.utility.offline.EntityModel
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector

@Entity
data class SubSectorEntity(
    val icon: String = "",
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val name: String = "",
    val sectorId: Int = 0,
    val sectorName: String = "",
    var commodities: String = "",
    val status: String = "",
    val fieldAssistantId: String = "",
    val fieldAssistantName: String = "",
    val description: String = "",
    val scheduleStatus: String = "",
    val scheduleDescription: String = ""
) : EntityModel {
    fun toSubSector() = SubSector(
        icon = icon,
        id = id,
        name = name,
        sectorId = sectorId,
        sectorName = sectorName,
        commodities = toObject(commodities) ?: emptyList(),
        status = status,
        fieldAssistantId = fieldAssistantId,
        fieldAssistantName = fieldAssistantName,
        description = description,
        scheduleStatus = scheduleStatus,
        scheduleDescription = scheduleDescription
    )
}
