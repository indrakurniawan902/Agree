package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.cultivator

import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCultivator

interface LoanSubmissionCultivatorDataContract {
    fun checkMemberEligibilityOnDefault()
    fun fetchCheckMemberEligibility(mitraId: String, loanPackageId: String)

    fun checkMemberEligibilityOnLoading()

    fun checkMemberEligibilityOnSuccess(data: List<ApplyLoanCultivator>)

    fun checkMemberEligibilityOnEmpty(data: List<ApplyLoanCultivator>)

    fun checkMemberEligibilityOnError(e: Throwable?)
}
