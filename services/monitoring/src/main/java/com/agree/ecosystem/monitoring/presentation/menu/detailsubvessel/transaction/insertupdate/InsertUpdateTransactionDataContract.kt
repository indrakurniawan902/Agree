package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.insertupdate

import com.agree.ecosystem.monitoring.domain.reqres.model.detailcompany.DetailCompany
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionDetail

interface InsertUpdateTransactionDataContract {
    fun extractValueThenInsertOrUpdate()

    fun doInsertTransaction()

    fun doUpdateTransaction()

    fun onInsertTransactionLoading()

    fun onInsertTransactionSuccess()

    fun onInsertTransactionFailed(e: Throwable?)

    fun onTransactionDetailLoading()

    fun onGetTransactionDetailSuccess(data: TransactionDetail)

    fun onTransactionDetailFailed(e: Throwable?)

    fun onUpdateTransactionLoading()

    fun onUpdateTransactionSuccess()

    fun onUpdateTransactionFailed(e: Throwable?)

    fun onGetProductTypeLoading()

    fun onGetProductTypeSuccess(data: DetailCompany)

    fun onGetProductTypeFailed(e: Throwable?)
}
