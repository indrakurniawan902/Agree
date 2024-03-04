package com.agree.ecosystem.finances.domain.reqres.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class MyLoanData(
    val isActive: Boolean,
    val loans: List<MyLoan>
) : Parcelable

@Keep
@Parcelize
data class MyLoan(
    val loanId: String,
    val loanGroupCode: String,
    val loanRequestedAmount: Long,
    val loanApprovalAmount: Long,
    val loanDisbursedAmount: Long,
    val loanBorrowers: List<String>,
    val loanPackageName: String,
    val loanSubmissionDateTime: String,
    val dateTime: String
) : Parcelable