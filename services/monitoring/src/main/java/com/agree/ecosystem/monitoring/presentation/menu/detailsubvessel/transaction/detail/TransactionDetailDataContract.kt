package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.detail

import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionDetail

interface TransactionDetailDataContract {
    fun onTransactionDetailLoading()

    fun onGetTransactionDetailSuccess(data: TransactionDetail)

    fun onTransactionDetailFailed(e: Throwable?)
}
