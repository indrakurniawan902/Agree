package com.agree.ecosystem.partnership.data.reqres.model.companymember

import com.agree.ecosystem.partnership.data.reqres.model.company.PartnerTypeItem
import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMember
import com.agree.ecosystem.utilities.data.reqres.model.commodity.CommodityItem
import com.google.gson.annotations.SerializedName

data class CompanyMemberItem(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("company_id")
    val companyId: String? = null,
    @field:SerializedName("company_name")
    val companyName: String? = null,
    @field:SerializedName("company_image")
    val companyImage: String? = null,
    @field:SerializedName("commodities")
    val commodities: List<CommodityItem>? = null,
    @field:SerializedName("partner_type")
    val partnerType: List<PartnerTypeItem>? = null,
    @field:SerializedName("user_id")
    val userId: String? = null,
    @field:SerializedName("submission_id")
    val submissionId: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("NIK")
    val nik: String? = null,
    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("address")
    val address: String? = null,
    @field:SerializedName("avatar")
    val avatar: String? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("payment_status")
    val paymentStatus: String? = null,
    @field:SerializedName("all_paid_status")
    val allPaidStatus: String? = null,
    @field:SerializedName("start_date")
    val startDate: String? = null,
    @field:SerializedName("end_date")
    val endDate: String? = null,
    @field:SerializedName("created_at")
    val createdAt: String? = null,
    @field:SerializedName("created_by")
    val createdBy: String? = null,
    @field:SerializedName("modified_at")
    val modifiedAt: String? = null,
    @field:SerializedName("modified_by")
    val modifiedBy: String? = null
) {
    fun toCompanyMember(): CompanyMember {
        return CompanyMember(
            id = id.orEmpty(),
            companyId = companyId.orEmpty(),
            companyName = companyName.orEmpty(),
            companyImage = companyImage.orEmpty(),
            commodities = commodities?.map { it.toCommodity() }.orEmpty(),
            partnerType = partnerType?.map { it.toPartnerType() }.orEmpty(),
            userId = userId.orEmpty(),
            submissionId = submissionId.orEmpty(),
            name = name.orEmpty(),
            nik = nik.orEmpty(),
            phoneNumber = phoneNumber.orEmpty(),
            email = email.orEmpty(),
            address = address.orEmpty(),
            avatar = avatar.orEmpty(),
            status = status == "active",
            paymentStatus = paymentStatus.orEmpty(),
            allPaidStatus = allPaidStatus.orEmpty(),
            startDate = startDate.orEmpty(),
            endDate = endDate.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty()
        )
    }
}
