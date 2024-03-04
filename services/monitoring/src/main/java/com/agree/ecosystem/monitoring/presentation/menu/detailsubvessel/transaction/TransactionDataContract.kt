package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction

import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.Transaction
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionSummary
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.SharedComponentDataContract
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselDataContract

interface TransactionDataContract : SubVesselDataContract, SharedComponentDataContract {

    fun fetchTransactionList(id: String)

    fun onGetTransactionsLoading()

    fun onGetTransactionsSuccess(data: List<Transaction>)

    fun onGetTransactionsFailed(e: Throwable?)

    fun onTransactionsEmpty()

    fun fetchLoadMoreTransactions()

    fun onLoadMoreSuccess(data: List<Transaction>)

    fun onLoadMoreLoading()

    fun onLoadMoreFailed()

    fun onLoadMoreEmpty()

    fun fetchTransactionSummary(id: String)

    fun onTransactionSummaryLoading()

    fun onTransactionSummarySuccess(data: TransactionSummary)

    fun onTransactionSummaryFailed(e: Throwable?)
}
