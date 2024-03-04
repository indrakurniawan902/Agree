package com.agree.ecosystem.monitoring.data.reqres.model.subvessel

import com.agree.ecosystem.core.utils.utility.offline.WebModel
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.google.gson.annotations.SerializedName

data class SubVesselItem(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("vessel_id")
    val vesselId: String? = null,
    @SerializedName("company_member_id")
    val companyMemberId: String? = null,
    @SerializedName("company_submember_id")
    val companySubMemberId: String? = null,
    @field:SerializedName("company_name")
    val companyName: String? = null,
    @SerializedName("subsector_id")
    val subSectorId: String? = null,
    @SerializedName("comodity_id")
    val commodityId: String? = null,
    @SerializedName("comodity_variety_id")
    val commodityVarietyId: String? = null,
    @SerializedName("commodity_name")
    val commodityName: String? = null,
    @SerializedName("district_id")
    val districtId: String? = null,
    @SerializedName("district_name")
    val districtName: String? = null,
    @SerializedName("subvessels_name")
    val subVesselName: String? = null,
    @SerializedName("order")
    val order: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("date_planted")
    val datePlanted: String? = null,
    @SerializedName("company_id")
    val companyId: String? = null,
    @SerializedName("size")
    val size: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("created_by")
    val createdBy: String? = null,
    @SerializedName("modified_at")
    val modifiedAt: String? = null,
    @SerializedName("modified_by")
    val modifiedBy: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @field:SerializedName("target")
    val target: Double? = null,
    @field:SerializedName("variety_name")
    val varietyName: String? = null,
    @field:SerializedName("vessel_name")
    val vesselName: String? = null,
    @SerializedName("worker_name")
    val workerName: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("sectors_name")
    val sectorName: String? = null,
    @SerializedName("sector_id")
    val sectorId: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("has_smartfarm")
    val hasSmartfarm: Boolean? = null,
) : WebModel {
    fun toSubVesselEntity(): SubVesselEntity {
        return SubVesselEntity(
            id = id.orEmpty(),
            vesselId = vesselId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            companySubMemberId = companyMemberId.orEmpty(),
            companyName = companyName.orEmpty(),
            subSectorId = subSectorId.orEmpty(),
            commodityId = commodityId.orEmpty(),
            commodityVarietyId = commodityVarietyId.orEmpty(),
            commodityName = commodityName.orEmpty(),
            districtId = districtId.orEmpty(),
            districtName = districtName.orEmpty(),
            subVesselName = subVesselName.orEmpty(),
            order = order.orEmpty(),
            status = when (status) {
                "active",
                "approved" -> SubVessel.Status.ACTIVE
                else -> SubVessel.Status.INACTIVE
            },
            datePlanted = datePlanted.orEmpty(),
            companyId = companyId.orEmpty(),
            size = size.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            code = code.orEmpty(),
            target = target ?: 0.0,
            varietyName = varietyName.orEmpty(),
            vesselName = vesselName.orEmpty(),
            workerName = workerName.orEmpty(),
            description = description.orEmpty(),
            sectorName = sectorName.orEmpty(),
            sectorId = sectorId.orEmpty(),
            name = name.orEmpty(),
            hasSmartfarm = hasSmartfarm ?: false
        )
    }
    fun toSubVessel(): SubVessel {
        return SubVessel(
            id = id.orEmpty(),
            vesselId = vesselId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            companySubMemberId = companyMemberId.orEmpty(),
            companyName = companyName.orEmpty(),
            subSectorId = subSectorId.orEmpty(),
            commodityId = commodityId.orEmpty(),
            commodityVarietyId = commodityVarietyId.orEmpty(),
            commodityName = commodityName.orEmpty(),
            districtId = districtId.orEmpty(),
            districtName = districtName.orEmpty(),
            subVesselName = subVesselName.orEmpty(),
            order = order.orEmpty(),
            status = when (status) {
                "active",
                "approved" -> SubVessel.Status.ACTIVE
                else -> SubVessel.Status.INACTIVE
            },
            datePlanted = datePlanted.orEmpty(),
            companyId = companyId.orEmpty(),
            size = size.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            code = code.orEmpty(),
            target = target ?: 0.0,
            varietyName = varietyName.orEmpty(),
            vesselName = vesselName.orEmpty(),
            workerName = workerName.orEmpty(),
            description = description.orEmpty(),
            sectorName = sectorName.orEmpty(),
            sectorId = sectorId.orEmpty(),
            name = name.orEmpty(),
            hasSmartfarm = hasSmartfarm ?: false
        )
    }
}
