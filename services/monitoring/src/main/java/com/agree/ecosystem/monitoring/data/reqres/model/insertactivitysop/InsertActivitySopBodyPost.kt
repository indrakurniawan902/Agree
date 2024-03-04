package com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop

import com.google.gson.annotations.SerializedName

data class InsertActivitySopBodyPost(
    @SerializedName("commodity_id")
    val commodityId: String,
    @SerializedName("commodity_variety_id")
    val commodityVarietyId: String,
    @SerializedName("company_id")
    val companyId: String,
    @SerializedName("company_member_id")
    val companyMemberId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("data")
    val `data`: String,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("modified_at")
    val modifiedAt: String,
    @SerializedName("modified_by")
    val modifiedBy: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: String,
    @SerializedName("phase_activity_id")
    val phaseActivityId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("subvessel_id")
    val subvesselId: String,
    @SerializedName("vessel_id")
    val vesselId: String,
    @SerializedName("worker_id")
    val workerId: String,
    @SerializedName("individual_id")
    val individualId: String
)
