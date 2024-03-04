package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.detail

import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage

interface DetailLoanPackgeDataContract {
    fun fetchDetailLoanPackage(loanPackageId: String)

    fun detailLoanPackageOnLoading()

    fun detailLoanPackageOnSuccess(data: DetailLoanPackage)

    fun detailLoanPackageOnError(e: Throwable?)
}
