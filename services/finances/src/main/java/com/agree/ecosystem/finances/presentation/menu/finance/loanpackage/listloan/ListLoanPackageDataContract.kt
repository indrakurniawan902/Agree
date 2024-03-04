package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan

import com.agree.ecosystem.finances.domain.reqres.model.LoanPackage

interface ListLoanPackageDataContract {

    fun fetchlistLoanPackage()
    fun listLoanPackageOnLoading()
    fun listLoanPackageOnSuccess(data: List<LoanPackage>)
    fun listLoanPackageOnError(e: Throwable?)
    fun listLoanPackageOnEmpty()

    fun fetchLoadMoreLoanPackage()
    fun loadMorelistLoanPackageOnLoading()
    fun loadMorelistLoanPackageOnSuccess(data: List<LoanPackage>)
    fun loadMorelistLoanPackageOnError(e: Throwable?)
    fun loadMorelistLoanPackageOnEmpty()
}
