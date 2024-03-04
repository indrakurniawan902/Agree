package com.agree.ecosystem.finances.data.reqres.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApplyLoanBodyPost(
    @SerializedName("loanUserId") val loanUserId: String,
    @SerializedName("loanPackageId") val loanPackageId: String,
    @SerializedName("loanRequestedData") val loanRequestedData: List<LoanRequestedData>,
    @SerializedName("loanSubmissionType") var loanSubmissionType: String = "internal"
)

data class LoanRequestedData(
    @SerializedName("loanRequestedAmount") val loanRequestedAmount: Double,
    @SerializedName("loanItemList") val loanItemList: List<LoanItemList>,
    @SerializedName("loanMitraId") val loanMitraId: String,
    @SerializedName("loanPlantingSeasonIds") val loanPlantingSeasonIds: List<String>,
    @SerializedName("loanCollateralId") val loanCollateralId: String?,
    @SerializedName("loanBorrowerId") val loanBorrowerId: String,
    @SerializedName("loanFarmerId") val loanFarmerId: String,
    @SerializedName("loanCompanyId") val loanCompanyId: String
)

data class LoanItemList(
    @SerializedName("itemName") val itemName: String,
    @SerializedName("itemPrice") val itemPrice: Int,
    @SerializedName("itemQuantity") val itemQuantity: Int,
    @SerializedName("itemUnit") val itemUnit: String,
    @SerializedName("itemMaxUnit") val itemMaxUnit: Int,
    @SerializedName("itemUnitRequest") val itemUnitRequest: Int
)