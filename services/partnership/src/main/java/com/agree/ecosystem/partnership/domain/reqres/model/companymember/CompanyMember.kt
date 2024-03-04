package com.agree.ecosystem.partnership.domain.reqres.model.companymember

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.partnership.domain.reqres.model.company.PartnerType
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CompanyMember(
    val id: String,
    val companyId: String,
    val companyName: String,
    val companyImage: String,
    val commodities: List<Commodity>,
    val partnerType: List<PartnerType>,
    val userId: String,
    val submissionId: String,
    val name: String,
    val nik: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val avatar: String,
    val status: Boolean,
    val paymentStatus: String,
    val allPaidStatus: String,
    val startDate: String,
    val endDate: String,
    val createdAt: String,
    val createdBy: String,
    val modifiedAt: String,
    val modifiedBy: String
) : Parcelable
