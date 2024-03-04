package com.agree.ecosystem.monitoring.data.reqres.model.vessel

import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import com.google.gson.annotations.SerializedName

data class VesselItem(
    @field:SerializedName("address")
    val address: String?,

    @field:SerializedName("code")
    val code: String?,

    @field:SerializedName("company_id")
    val companyId: String?,

    @field:SerializedName("company_member_d")
    val companyMemberId: String?,

    @field:SerializedName("company_member_worker_id")
    val companyMemberWorkerId: String?,

    @field:SerializedName("created_at")
    val createdAt: String?,

    @field:SerializedName("created_by")
    val createdBy: String?,

    @field:SerializedName("district_id")
    val districtId: String?,

    @field:SerializedName("district_name")
    val districtName: String?,

    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("modified_at")
    val modifiedAt: String?,

    @field:SerializedName("modified_by")
    val modifiedBy: String?,

    @field:SerializedName("vessel_name")
    val name: String?,

    @field:SerializedName("province_id")
    val provinceId: String?,

    @field:SerializedName("province_name")
    val provinceName: String?,

    @field:SerializedName("realization_size")
    val realizationSize: Float?,

    @field:SerializedName("size")
    val size: Float?,

    @field:SerializedName("subdistrict_id")
    val subdistrictId: String?,

    @field:SerializedName("village_id")
    val villageId: String?,

    @field:SerializedName("worker_id")
    val workerId: String?
) {
    fun toVesselEntity(): VesselEntity {
        return VesselEntity(
            id = id.orEmpty(),
            address = address.orEmpty(),
            code = code.orEmpty(),
            companyId = companyId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            companyMemberWorkerId = companyMemberWorkerId.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            districtId = districtId.orEmpty(),
            districtName = districtName.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            name = name.orEmpty(),
            provinceId = provinceId.orEmpty(),
            provinceName = provinceName.orEmpty(),
            realizationSize = realizationSize ?: 0f,
            size = size ?: 0f,
            subdistrictId = subdistrictId.orEmpty(),
            villageId = villageId.orEmpty(),
            workerId = workerId.orEmpty()
        )
    }

    fun toVessel(): Vessel {
        return Vessel(
            address = address.orEmpty(),
            code = code.orEmpty(),
            companyId = companyId.orEmpty(),
            companyMemberId = companyMemberId.orEmpty(),
            companyMemberWorkerId = companyMemberWorkerId.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            districtId = districtId.orEmpty(),
            districtName = districtName.orEmpty(),
            id = id.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty(),
            name = name.orEmpty(),
            provinceId = provinceId.orEmpty(),
            provinceName = provinceName.orEmpty(),
            realizationSize = realizationSize ?: 0F,
            size = size ?: 0F,
            subdistrictId = subdistrictId.orEmpty(),
            villageId = villageId.orEmpty(),
            workerId = workerId.orEmpty(),
        )
    }
}
