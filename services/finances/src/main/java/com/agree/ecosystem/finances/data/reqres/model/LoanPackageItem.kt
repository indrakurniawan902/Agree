package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.FinanceSubSectorLoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackageCompany
import com.google.gson.annotations.SerializedName

data class LoanPackageItem(
    @field:SerializedName("mitraId")
    val mitraId: String? = null,
    @field:SerializedName("companyId")
    val companyId: String? = null,
    @field:SerializedName("companyName")
    val companyName: String? = null,
    @field:SerializedName("companyImage")
    val companyImage: String? = null,
    @field:SerializedName("subsectors")
    val subsector: List<FinanceSubSectorLoanPackageItem>? = listOf(),
    @field:SerializedName("loanPackage")
    val loanPackage: List<LoanPackageCompanyItem>? = listOf()
) {
    fun toLoanPackageModel(): LoanPackage {
        return LoanPackage(
            mitraId ?: "",
            companyId ?: "",
            companyName ?: "",
            companyImage ?: "",
            subsector?.map { it.toFinanceSubSectorLoanPackage() } ?: listOf(),
            loanPackage?.map { it.toLoanPackageCompanyModel() } ?: listOf()
        )
    }
}

data class FinanceSubSectorLoanPackageItem(
    @field:SerializedName("sectorId")
    val sectorId: String? = "",
    @field:SerializedName("sectorName")
    val sectorName: String? = "",
    @field:SerializedName("subSectorId")
    val subSectorId: String? = "",
    @field:SerializedName("subsectorName")
    val subSectorName: String? = ""
) {
    fun toFinanceSubSectorLoanPackage() = FinanceSubSectorLoanPackage(
        sectorId ?: "", sectorName ?: "", subSectorId ?: "", subSectorName ?: ""
    )
}

data class LoanPackageCompanyItem(
    @field:SerializedName("loanPackageId")
    val loanPackageId: String? = null,
    @field:SerializedName("loanPackageName")
    val loanPackageName: String? = null,
    @field:SerializedName("image")
    val image: String? = null,
    @field:SerializedName("loanPackageFor")
    val loanPackageFor: String? = null,
    @field:SerializedName("loanPackageMaxMount")
    val loanPackageMaxMount: Double? = 0.0,
    @field:SerializedName("loanPackageMinMount")
    val loanPackageMinMount: Double? = 0.0,
    @field:SerializedName("loanPackagePaymentType")
    val loanPackagePaymentType: String? = null,
    @field:SerializedName("loanPackageType")
    val loanPackageType: String? = null
) {
    fun toLoanPackageCompanyModel(): LoanPackageCompany {
        return LoanPackageCompany(
            loanPackageId ?: "",
            loanPackageName ?: "",
            image ?: "",
            loanPackageFor ?: "",
            loanPackageMaxMount ?: 0.0,
            loanPackageMaxMount ?: 0.0,
            loanPackageType ?: "",
            loanPackagePaymentType ?: "",
        )
    }
}
