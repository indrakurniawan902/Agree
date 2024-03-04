package com.agree.ecosystem.partnership.data.reqres.model.vessel

import com.agree.ecosystem.partnership.domain.reqres.model.vessel.Vessel
import com.google.gson.annotations.SerializedName

data class VesselItem(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("vessel_id")
    val vesselId: String? = null,
    @SerializedName("company_member_id")
    val companyMemberId: String? = null,
    @SerializedName("company_member_worker_id")
    val companyMemberWorkerId: String? = null,
    @SerializedName("worker_id")
    val workerId: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("province_id")
    val provinceId: String? = null,
    @SerializedName("province_name")
    val provinceName: String? = null,
    @SerializedName("district_id")
    val districtId: String? = null,
    @SerializedName("subdistrict_id")
    val subDistrictId: String? = null,
    @SerializedName("village_id")
    val villageId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("realization_size")
    val realizationSize: Double? = null,
    @SerializedName("size")
    val size: Double? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("created_by")
    val createdBy: String? = null,
    @SerializedName("modified_at")
    val modifiedAt: String? = null,
    @SerializedName("modified_by")
    val modifiedBy: String? = null,
    @SerializedName("code")
    val code: String? = null
) {
    fun toVessel(): Vessel {
        return Vessel(
            id = id.orEmpty(),
            vesselId = vesselId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            companyMemberWorkerId = companyMemberWorkerId.orEmpty(),
            workerId = workerId.orEmpty(),
            address = address.orEmpty(),
            provinceId = provinceId.orEmpty(),
            provinceName = provinceName.orEmpty(),
            districtId = districtId.orEmpty(),
            subDistrictId = subDistrictId.orEmpty(),
            villageId = villageId.orEmpty(),
            name = name.orEmpty(),
            realizationSize = realizationSize ?: 0.0,
            size = size ?: 0.0,
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            code = code.orEmpty()
        )
    }
}
