package com.agree.ecosystem.finances.presentation.menu.finance.loanactive

import com.agree.ecosystem.finances.domain.reqres.model.MyLoanData

interface ListLoanActiveDataContract {

    fun fetchListLoanActive(userId: String)

    fun listLoanActiveOnLoading()

    fun listLoanActiveOnSuccess(data: List<MyLoanData>)

    fun listLoanActiveOnEmpty(data: List<MyLoanData>)

    fun listLoanActiveOnError(e: Throwable?)

    fun fetchLoadMoreListLoanActive(userId: String)

    fun loadMoreListLoanActiveOnLoading()

    fun loadMoreListLoanActiveOnSuccess(data: List<MyLoanData>)

    fun loadMoreListLoanActiveOnEmpty(data: List<MyLoanData>)

    fun loadMoreListLoanActiveOnError(e: Throwable?)
}
