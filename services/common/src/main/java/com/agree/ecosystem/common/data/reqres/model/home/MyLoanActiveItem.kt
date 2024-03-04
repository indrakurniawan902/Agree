package com.agree.ecosystem.common.data.reqres.model.home

import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoan
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoanActive
import com.google.gson.annotations.SerializedName

data class MyLoanActiveItem(

    @SerializedName("isActive")
    val isActive: Boolean? = false,

    @SerializedName("loans")
    val loans: List<MyLoanItem> = arrayListOf()
) {
    fun toMyLoanData(): MyLoanActive {
        return MyLoanActive(
            isActive = isActive ?: false,
            loans = loans.map { it.toMyLoan() }
        )
    }
}

data class MyLoanItem(

    @SerializedName("loanGroupId")
    val loanId: String? = null,

    @SerializedName("loanGroupCode")
    val loanGroupCode: String? = null,

    @SerializedName("loanRequestedAmount")
    val loanRequestedAmount: Int? = null,

    @SerializedName("loanApprovalAmount")
    val loanApprovalAmount: Int? = null,

    @SerializedName("loanDisbursedAmount")
    val loanDisbursedAmount: Int? = null,

    @SerializedName("loanBorrowers")
    val loanBorrowers: List<String>? = null,

    @SerializedName("loanPackageName")
    val loanPackageName: String? = null,

    @SerializedName("loanSubmissionDateTime")
    val loanSubmissionDateTime: String? = null,

    @SerializedName("dateTime")
    val dateTime: String? = null
) {
    fun toMyLoan(): MyLoan {
        return MyLoan(
            loanId = loanId.orEmpty(),
            loanGroupCode = loanGroupCode.orEmpty(),
            loanRequestedAmount = loanRequestedAmount ?: 0,
            loanApprovalAmount = loanApprovalAmount ?: 0,
            loanDisbursedAmount = loanDisbursedAmount ?: 0,
            loanBorrowers = loanBorrowers?.map { it } ?: emptyList(),
            loanPackageName = loanPackageName.orEmpty(),
            loanSubmissionDateTime = loanSubmissionDateTime.orEmpty(),
            dateTime = dateTime.orEmpty()
        )
    }
}
