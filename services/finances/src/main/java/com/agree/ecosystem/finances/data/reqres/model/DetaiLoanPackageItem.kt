package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackageDataSchema
import com.google.gson.annotations.SerializedName

data class DetaiLoanPackageItem(
    @field:SerializedName("loanPackageId")
    val loanPackageId: String? = null,

    @field:SerializedName("loanPackageName")
    val loanPackageName: String? = null,

    @field:SerializedName("loanPackageDescription")
    val loanPackageDescription: String? = null,

    @field:SerializedName("loanPackageMaxAmount")
    val loanPackageMaxAmount: Double? = 0.0,

    @field:SerializedName("loanPackageMinAmount")
    val loanPackageMinAmount: Double? = 0.0,

    @field:SerializedName("loanPackageImage")
    val loanPackageImage: String? = null,

    @field:SerializedName("loanPackagePaymentType")
    val loanPackagePaymentType: String? = null,

    @field:SerializedName("loanPackageInstallmentType")
    val loanPackageInstallmentType: String? = null,

    @field:SerializedName("loanPackageDataSchema")
    val loanPackageDataSchema: LoanPackageDataSchemaItem?,

    @field:SerializedName("loanPackageItemList")
    val loanPackageItemList: List<LoanPackageItemListItem>? = null
) {
    fun toDetailLoanPackage() = DetailLoanPackage(
        loanPackageId ?: "",
        loanPackageName ?: "",
        loanPackageDescription ?: "",
        loanPackageMaxAmount ?: 0.0,
        loanPackageMinAmount ?: 0.0,
        loanPackagePaymentType ?: "",
        loanPackageInstallmentType ?: "",
        loanPackageImage ?: "",
        loanPackageDataSchema?.toLoanPackageDataSchema() ?: LoanPackageDataSchema(),
        loanPackageItemList?.map { it.toLoanPackageItemList() } ?: emptyList()
    )
}

