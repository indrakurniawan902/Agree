package com.agree.ecosystem.utilities.data.reqres.model.subsector

import com.agree.ecosystem.core.utils.utility.offline.WebModel
import com.agree.ecosystem.utilities.data.reqres.model.commodity.CommodityItem
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.google.gson.annotations.SerializedName

data class SubSectorItem(
    @field:SerializedName("icon")
    val icon: String? = null,
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("sector_id")
    val sectorId: Int? = null,
    @field:SerializedName("sector_name")
    val sectorName: String? = null,
    @field:SerializedName("commodities")
    val commodities: List<CommodityItem>? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("field_assistant_id")
    val fieldAssistantId: String? = null,
    @field:SerializedName("field_assistant_name")
    val fieldAssistantName: String? = null,
    @field:SerializedName("schedule_status")
    val scheduleStatus: String? = null,
    @field:SerializedName("schedule_description")
    val scheduleDescription: String? = null,
    @field:SerializedName("data")
    val data: Data? = null
) : WebModel {
    fun toSubSector(): SubSector {
        return SubSector(
            icon = icon.orEmpty(),
            id = id.orEmpty(),
            name = name.orEmpty(),
            sectorId = sectorId ?: 0,
            sectorName = sectorName.orEmpty(),
            commodities = commodities?.map { it.toCommodity() }.orEmpty(),
            status = status.orEmpty(),
            fieldAssistantId = fieldAssistantId.orEmpty(),
            fieldAssistantName = fieldAssistantName.orEmpty(),
            description = data?.description.orEmpty(),
            scheduleStatus = scheduleStatus.orEmpty(),
            scheduleDescription = scheduleDescription.orEmpty()
        )
    }

    fun toSubSectorEntity(): SubSectorEntity {
        return SubSectorEntity(
            icon = icon.orEmpty(),
            id = id.orEmpty(),
            name = name.orEmpty(),
            sectorId = sectorId ?: 0,
            sectorName = sectorName.orEmpty(),
            commodities = toJson(commodities),
            status = status.orEmpty(),
            fieldAssistantId = fieldAssistantId.orEmpty(),
            fieldAssistantName = fieldAssistantName.orEmpty(),
            description = data?.description.orEmpty(),
            scheduleStatus = scheduleStatus.orEmpty(),
            scheduleDescription = scheduleDescription.orEmpty()
        )
    }

    data class Data(
        @SerializedName("description")
        val description: String? = null
    )
}
