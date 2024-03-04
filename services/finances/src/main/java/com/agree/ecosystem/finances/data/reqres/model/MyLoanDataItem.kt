package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.MyLoan
import com.agree.ecosystem.finances.domain.reqres.model.MyLoanData
import com.google.gson.annotations.SerializedName

data class MyLoanDataItem(

    @field:SerializedName("isActive")
    val isActive: Boolean? = false,

    @field:SerializedName("loans")
    val loans: List<MyLoanItem> = arrayListOf()
) {
    fun toMyLoanData(): MyLoanData {
        return MyLoanData(
            isActive = isActive ?: false,
            loans = loans.map { it.toMyLoan() }
        )
    }
}

data class MyLoanItem(

    @field:SerializedName("loanGroupId")
    val loanId: String? = null,

    @field:SerializedName("loanGroupCode")
    val loanGroupCode: String? = null,

    @field:SerializedName("loanRequestedAmount")
    val loanRequestedAmount: Long? = null,

    @field:SerializedName("loanApprovalAmount")
    val loanApprovalAmount: Long? = null,

    @field:SerializedName("loanDisbursedAmount")
    val loanDisbursedAmount: Long? = null,

    @field:SerializedName("loanBorrowers")
    val loanBorrowers: List<String>? = null,

    @field:SerializedName("loanPackageName")
    val loanPackageName: String? = null,

    @field:SerializedName("loanSubmissionDateTime")
    val loanSubmissionDateTime: String? = null,

    @field:SerializedName("dateTime")
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