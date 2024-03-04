package com.agree.ecosystem.common.domain.reqres.model.home.loan

data class MyLoanActive(
    val isActive: Boolean,
    val loans: List<MyLoan>
)

data class MyLoan(
    val loanId: String,
    val loanGroupCode: String,
    val loanRequestedAmount: Int,
    val loanApprovalAmount: Int,
    val loanDisbursedAmount: Int,
    val loanBorrowers: List<String>,
    val loanPackageName: String,
    val loanSubmissionDateTime: String,
    val dateTime: String
)
