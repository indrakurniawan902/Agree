package com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails

import com.agree.ecosystem.core.utils.utility.offline.WebModel
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.google.gson.annotations.SerializedName

data class DetailSubVesselItem(
    @field:SerializedName("activity_name")
    val activityName: String? = null,
    @field:SerializedName("bruto")
    val bruto: String? = null,
    @field:SerializedName("code")
    val code: String? = null,
    @field:SerializedName("commodity_id")
    val commodityId: String? = null,
    @field:SerializedName("commodity_name")
    val commodityName: String? = null,
    @field:SerializedName("commodity_variety_id")
    val commodityVarietyId: String? = null,
    @field:SerializedName("company_id")
    val companyId: String? = null,
    @field:SerializedName("company_member_id")
    val companyMemberId: String? = null,
    @field:SerializedName("company_submember_id")
    val companySubMemberId: String? = null,
    @field:SerializedName("created_at")
    val createdAt: String? = null,
    @field:SerializedName("created_by")
    val createdBy: String? = null,
    @field:SerializedName("cycle_count")
    val cycleCount: Int? = null,
    @field:SerializedName("date_planted")
    val datePlanted: String? = null,
    @field:SerializedName("district_id")
    val districtId: String? = null,
    @field:SerializedName("district_name")
    val districtName: String? = null,
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("is_deleted")
    val isDeleted: Boolean? = null,
    @field:SerializedName("modified_at")
    val modifiedAt: String? = null,
    @field:SerializedName("modified_by")
    val modifiedBy: String? = null,
    @field:SerializedName("netto")
    val netto: String? = null,
    @field:SerializedName("order")
    val order: String? = null,
    @field:SerializedName("population")
    val population: String? = null,
    @field:SerializedName("size")
    val size: String? = null,
    @field:SerializedName("sop_id")
    val sopId: String? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("subsector_id")
    val subSectorId: String? = null,
    @field:SerializedName("subvessels_name")
    val subVesselName: String? = null,
    @field:SerializedName("target")
    val target: String? = null,
    @field:SerializedName("variety_name")
    val varietyName: String? = null,
    @field:SerializedName("vessel_id")
    val vesselId: String? = null,
    @field:SerializedName("worker_id")
    val workerId: String? = null,
    @field:SerializedName("worker_name")
    val workerName: String? = null,
    @field:SerializedName("record_type")
    val recordType: String? = null,
    @field:SerializedName("has_smartfarm")
    val hasSmartfarm: Boolean? = null,
) : WebModel {
    fun toDetailSubVessel(): DetailSubVessel {
        return DetailSubVessel(
            id = id.orEmpty(),
            vesselId = vesselId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            companySubMemberId = companySubMemberId.orEmpty(),
            subSectorId = subSectorId.orEmpty(),
            commodityId = commodityId.orEmpty(),
            commodityVarietyId = commodityVarietyId.orEmpty(),
            districtId = districtId.orEmpty(),
            districtName = districtName.orEmpty(),
            order = order.orEmpty(),
            status = status.orEmpty(),
            datePlanted = datePlanted.orEmpty(),
            cycleCount = cycleCount ?: 0,
            population = population.orEmpty(),
            target = target.orEmpty(),
            bruto = bruto.orEmpty(),
            netto = netto.orEmpty(),
            companyId = companyId.orEmpty(),
            size = size.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            code = code.orEmpty(),
            sopId = sopId.orEmpty(),
            activityName = activityName.orEmpty(),
            commodityName = commodityName.orEmpty(),
            isDeleted = isDeleted ?: false,
            subVesselName = subVesselName.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            varietyName = varietyName.orEmpty(),
            workerId = workerId.orEmpty(),
            workerName = workerName.orEmpty(),
            recordType = recordType.orEmpty(),
            hasSmartfarm = hasSmartfarm ?: false,
        )
    }
    fun toDetailSubVesselEntity(): DetailSubVesselEntity {
        return DetailSubVesselEntity(
            id = id.orEmpty(),
            vesselId = vesselId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            companySubMemberId = companySubMemberId.orEmpty(),
            subSectorId = subSectorId.orEmpty(),
            commodityId = commodityId.orEmpty(),
            commodityVarietyId = commodityVarietyId.orEmpty(),
            districtId = districtId.orEmpty(),
            districtName = districtName.orEmpty(),
            order = order.orEmpty(),
            status = status.orEmpty(),
            datePlanted = datePlanted.orEmpty(),
            cycleCount = cycleCount ?: 0,
            population = population.orEmpty(),
            target = target.orEmpty(),
            bruto = bruto.orEmpty(),
            netto = netto.orEmpty(),
            companyId = companyId.orEmpty(),
            size = size.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            code = code.orEmpty(),
            sopId = sopId.orEmpty(),
            activityName = activityName.orEmpty(),
            commodityName = commodityName.orEmpty(),
            isDeleted = isDeleted ?: false,
            subVesselName = subVesselName.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            varietyName = varietyName.orEmpty(),
            workerId = workerId.orEmpty(),
            workerName = workerName.orEmpty(),
            recordType = recordType.orEmpty(),
            hasSmartfarm = hasSmartfarm ?: false,
        )
    }
}
