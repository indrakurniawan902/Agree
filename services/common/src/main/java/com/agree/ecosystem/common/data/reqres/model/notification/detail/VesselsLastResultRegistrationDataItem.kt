package com.agree.ecosystem.common.data.reqres.model.notification.detail

import com.agree.ecosystem.common.domain.reqres.model.notification.detail.DataSubVessle
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.SubVesselsLastResultRegistration
import com.agree.ecosystem.common.domain.reqres.model.notification.detail.VesselsLastResultRegistration
import com.google.gson.annotations.SerializedName

data class VesselsLastResultRegistrationDataItem(
    @field:SerializedName("address")
    val address: String?,

    @field:SerializedName("code")
    val code: String?,

    @field:SerializedName("created_at")
    val createdAt: String?,

    @field:SerializedName("created_by")
    val createdBy: String?,

    @field:SerializedName("data")
    val data: String?,

    @field:SerializedName("district_id")
    val districtId: String?,

    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("is_deleted")
    val isDeleted: Boolean?,

    @field:SerializedName("is_suitable")
    val isSuitable: Boolean?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("province_id")
    val provinceId: String?,

    @field:SerializedName("realization_size")
    val realizationSize: Double? = 0.00,

    @field:SerializedName("size")
    val size: Double? = 0.00,

    @field:SerializedName("status")
    val status: String?,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("vessel_name")
    val vesselName: String?,

    @field:SerializedName("user_id")
    val userId: String?,

    @field:SerializedName("worker_id")
    val workerId: String?,

    @field:SerializedName("subdistrict_id")
    val subDistrictId: String?,

    @field:SerializedName("village_id")
    val villageId: String?,

    @field:SerializedName("subvessel_count")
    val subvesselCount: Int?,

    @field:SerializedName("sub_vessels")
    val subVessels: List<SubVesselsLastResultRegistrationDataItem>? = null
) {
    fun toVesselsLastResultRegistration() = VesselsLastResultRegistration(
        vesselName = vesselName ?: "",
        userId = userId ?: "",
        workerId = workerId ?: "",
        size = size ?: 0.00,
        status = status ?: "",
        address = address ?: "",
        description = description ?: "",
        provinceId = provinceId ?: "",
        disctrictId = districtId ?: "",
        subDistrictId = subDistrictId ?: "",
        villageId = villageId ?: "",
        realizationSize = realizationSize ?: 0.00,
        cratedAt = createdAt ?: "",
        cratedBy = createdBy ?: "",
        location = location ?: "",
        subVesselCount = subvesselCount ?: -1,
        subVessel = subVessels?.map { it.toSubVesslesLastResultRegistration() } ?: listOf()
    )
}

data class DataSubVessleDataItem(
    @field:SerializedName("distance_to_settlement")
    val distanceToSettleMent: Int? = 0,

    @field:SerializedName("floor")
    val floor: Int? = 0,

    @field:SerializedName("fount")
    val fount: String? = "",

    @field:SerializedName("funding")
    val funding: String? = "",

    @field:SerializedName("humidity")
    val humidity: String? = "",

    @field:SerializedName("legality")
    val legality: String? = "",

    @field:SerializedName("rainfall")
    val rainfall: String? = "",

    @field:SerializedName("sunshine")
    val sunshine: String? = "",

    @field:SerializedName("survey_note")
    val surveyNote: String? = "",

    @field:SerializedName("temperature")
    val temperature: Int? = 0,

    @field:SerializedName("type_of_subarea")
    val typeOfSubarea: String? = "",

    @field:SerializedName("wind_velocity")
    val windVelocity: Int? = 0
) {
    fun toDataSubVeslle() = DataSubVessle(
        distanceToSettleMent ?: 0,
        floor ?: 0,
        fount.orEmpty(),
        funding.orEmpty(),
        humidity.orEmpty(),
        legality.orEmpty(),
        rainfall.orEmpty(),
        sunshine.orEmpty(),
        surveyNote.orEmpty(),
        temperature ?: 0,
        typeOfSubarea.orEmpty(),
        windVelocity ?: 0
    )
}

data class SubVesselsLastResultRegistrationDataItem(
    @field:SerializedName("code")
    val code: String? = "",

    @field:SerializedName("commodity_id")
    val commodityId: String? = "",

    @field:SerializedName("commodity_name")
    val commodityName: String? = "",

    @field:SerializedName("commodity_variety_id")
    val commodityVarietyId: String? = "",

    @field:SerializedName("created_at")
    val createdAt: String? = "",

    @field:SerializedName("data")
    val data: DataSubVessleDataItem? = null,

    @field:SerializedName("description")
    val description: String? = "",

    @field:SerializedName("dynamic_view")
    val dynamicView: Any? = null,

    @field:SerializedName("id")
    val id: String? = "",

    @field:SerializedName("is_suitable")
    val isSuitable: Boolean?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("sectors_name")
    val sectorsName: String? = "",

    @field:SerializedName("size")
    val size: Double?,

    @field:SerializedName("status")
    val status: String? = "",

    @field:SerializedName("submission_id")
    val submissionId: String? = "",

    @field:SerializedName("submission_vessel_id")
    val submissionVesselId: String? = "",

    @field:SerializedName("subsector_id")
    val subSectorId: String? = "",

    @field:SerializedName("subvessel_coordinate")
    val subvesselCoordinate: String? = "",

    @field:SerializedName("worker_id")
    val workerId: String? = "",

    @field:SerializedName("worker_name")
    val workerName: String? = ""
) {
    fun toSubVesslesLastResultRegistration() = SubVesselsLastResultRegistration(
        id = id ?: "",
        subvesselName = name ?: "",
        submissionId = submissionId ?: "",
        submissionCessselId = submissionVesselId ?: "",
        workerId = workerId ?: "",
        subSectorId = subSectorId ?: "",
        size = size ?: 0.00,
        createdAt = createdAt ?: "",
        data = data?.toDataSubVeslle() ?: DataSubVessle(0, 0, "", "", "", "", "", "", "", 0, "", 0),
        commodityId = commodityId ?: "",
        commodityName = commodityName ?: "",
        workerName = workerName ?: "",
        sectorName = sectorsName ?: "",
        status = status ?: "",
        description = description ?: "",
        dynamicView = dynamicView.toString()
    )
}
