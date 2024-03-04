package com.agree.ecosystem.partnership.data.reqres.model.registrationstatusdetails

import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.FinalAssessmentStatus
import com.google.gson.annotations.SerializedName

data class FinalAssessmentStatusItem(
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("created_by")
    val createdBy: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("district_id")
    val districtId: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("mid_lat")
    val midLat: String? = null,
    @SerializedName("mid_long")
    val midLong: String? = null,
    @SerializedName("province_id")
    val provinceId: String? = null,
    @SerializedName("realization_size")
    val realizationSize: Double? = null,
    @SerializedName("size")
    val size: Double? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("sub_vessels")
    val subVessels: List<SubVesselItem>? = null,
    @SerializedName("subdistrict_id")
    val subdistrictId: String? = null,
    @SerializedName("subsector_id")
    val subsectorId: String? = null,
    @SerializedName("subvessel_count")
    val subvesselCount: Int? = null,
    @SerializedName("user_id")
    val userId: String? = null,
    @SerializedName("vessel_name")
    val vesselName: String? = null,
    @SerializedName("village_id")
    val villageId: String? = null,
    @SerializedName("worker_id")
    val workerId: String? = null
) {
    fun toFinalAssessmentStatus(): FinalAssessmentStatus {
        return FinalAssessmentStatus(
            address = address.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            description = description.orEmpty(),
            districtId = districtId.orEmpty(),
            id = id.orEmpty(),
            location = location.orEmpty(),
            midLat = midLat.orEmpty(),
            midLong = midLong.orEmpty(),
            provinceId = provinceId.orEmpty(),
            realizationSize = realizationSize ?: 0.0,
            size = size ?: 0.0,
            status = status == "approved",
            subVessels = subVessels?.map { it.toSubVessel() }.orEmpty(),
            subDistrictId = subdistrictId.orEmpty(),
            subSectorId = subdistrictId.orEmpty(),
            subVesselCount = subvesselCount ?: 0,
            userId = userId.orEmpty(),
            vesselName = vesselName.orEmpty(),
            villageId = villageId.orEmpty(),
            workerId = workerId.orEmpty()
        )
    }
}
