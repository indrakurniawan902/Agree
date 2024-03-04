package com.agree.ecosystem.finances.domain.reqres.model.params

import android.os.Parcelable
import com.agree.ecosystem.finances.domain.reqres.model.CultivatorBorrower
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCultivator
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileCultivatorParams(
    val cultivator: CultivatorBorrower? = null,
    val applyLoanCultivator: ApplyLoanCultivator? = null,
    val detailLoanPackage: DetailLoanPackage?,
    val isFromLoan: Boolean = false,
    val requiredFields: List<String>? = listOf()
) : Parcelable

@Parcelize
data class DynamicFormProfileCultivatorParams(
    val farmerBorrowerId: String,
    val schemeName: String,
    val fragmentTitle: String,
    val requiredFields: Array<String>?
) : Parcelable
