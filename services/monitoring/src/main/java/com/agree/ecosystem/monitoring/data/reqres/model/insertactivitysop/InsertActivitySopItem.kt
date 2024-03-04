package com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.InsertActivitySop
import com.google.gson.annotations.SerializedName

class InsertActivitySopItem(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("commodity_id")
    val commodityId: String? = null,
    @SerializedName("commodity_variety_id")
    val commodityVarietyId: String? = null,
    @SerializedName("company_id")
    val companyId: String? = null,
    @SerializedName("company_member_id")
    val companyMemberId: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("created_by")
    val createdBy: String? = null,
    @SerializedName("data")
    val `data`: String? = null,
    @SerializedName("date_time")
    val dateTime: String? = null,
    @SerializedName("modified_at")
    val modifiedAt: String? = null,
    @SerializedName("modified_by")
    val modifiedBy: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("order")
    val order: String? = null,
    @SerializedName("phase_activity_id")
    val phaseActivityId: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("subvessel_id")
    val subvesselId: String? = null,
    @SerializedName("vessel_id")
    val vesselId: String? = null,
    @SerializedName("worker_id")
    val workerId: String? = null,
    @SerializedName("individual_id")
    val individualId: String? = null
) {
    fun toInsertActivitySop(): InsertActivitySop {
        return InsertActivitySop(
            id = id.orEmpty(),
            commodityId = commodityId.orEmpty(),
            commodityVarietyId = commodityVarietyId.orEmpty(),
            companyId = companyId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            data = data.orEmpty(),
            dateTime = dateTime.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            name = name.orEmpty(),
            order = order.orEmpty(),
            phaseActivityId = phaseActivityId.orEmpty(),
            status = status.orEmpty(),
            subvesselId = subvesselId.orEmpty(),
            vesselId = vesselId.orEmpty(),
            workerId = workerId.orEmpty(),
            individualId = individualId.orEmpty()
        )
    }
}
