package com.agree.ecosystem.partnership.data.reqres.model.registrationstatus

import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.agree.ecosystem.utilities.data.reqres.model.commodity.CommodityItem
import com.google.gson.annotations.SerializedName

data class RegistrationStatusItem(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("company_id")
    val companyId: String? = null,
    @field:SerializedName("size")
    val size: Double? = null,
    @field:SerializedName("type")
    val type: String? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("address")
    val address: String? = null,
    @field:SerializedName("province_id")
    val provinceId: String? = null,
    @field:SerializedName("district_id")
    val districtId: String? = null,
    @field:SerializedName("subdistrict_id")
    val subDistrictId: String? = null,
    @field:SerializedName("village_id")
    val villageId: String? = null,
    @field:SerializedName("created_at")
    val createdAt: String? = null,
    @field:SerializedName("created_by")
    val createdBy: String? = null,
    @field:SerializedName("modified_at")
    val modifiedAt: String? = null,
    @field:SerializedName("company_logo")
    val companyLogo: String? = null,
    @field:SerializedName("company_name")
    val companyName: String? = null,
    @field:SerializedName("company_status")
    val companyStatus: String? = null,
    @field:SerializedName("commodity")
    val commodity: List<CommodityItem>? = null
) {

    fun toRegistrationStatus(): RegistrationStatus {
        return RegistrationStatus(
            id = id.orEmpty(),
            companyId = companyId.orEmpty(),
            size = size ?: 0.0,
            type = type.orEmpty(),
            status = when (status) {
                "pending" -> RegistrationStatus.Status.SUBMITTED
                "assigned" -> RegistrationStatus.Status.ON_PROGRESS
                "surveyed" -> RegistrationStatus.Status.ON_SURVEY
                "approved" -> RegistrationStatus.Status.ACCEPTED
                "rejected" -> RegistrationStatus.Status.REJECTED
                "cancelled" -> RegistrationStatus.Status.CANCELED
                else -> RegistrationStatus.Status.SUBMITTED
            },
            address = address.orEmpty(),
            provinceId = provinceId.orEmpty(),
            districtId = districtId.orEmpty(),
            subDistrictId = subDistrictId.orEmpty(),
            villageId = villageId.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            companyLogo = companyLogo.orEmpty(),
            companyName = companyName.orEmpty(),
            companyStatus = companyStatus.orEmpty(),
            commodity = commodity?.map { it.toCommodity() }.orEmpty()
        )
    }
}
