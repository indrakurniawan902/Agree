package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionParams
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.Transaction
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionSummary
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class TransactionViewModel(
    private val usecase: MonitoringUseCase,
) : DevViewModel() {

    var page = 1

    private val _transactions = DevData<List<Transaction>>().apply { default() }
    val transactions = _transactions.immutable()

    private val _loadMoreTransactions = DevData<List<Transaction>>().apply { default() }
    val loadMoreTransactions = _loadMoreTransactions.immutable()

    private val _transactionSummary = DevData<TransactionSummary>().apply { default() }
    val transactionSummary = _transactionSummary.immutable()

    fun getListTransaction(subVesselId: String) {
        page = 1
        usecase.getListTransaction(TransactionParams(page = page, 10, TransactionParams.Query(subVesselId)))
            .setHandler(_transactions)
            .let { return@let disposable::add }
    }

    fun loadMoreListTransaction(subVesselId: String) {
        usecase.getListTransaction(TransactionParams(page, 10, TransactionParams.Query(subVesselId)))
            .setHandler(_loadMoreTransactions)
            .let { return@let disposable::add }
    }

    fun getTransactionSummary(subVesselId: String) {
        usecase.getTransactionSummary(subVesselId)
            .setHandler(_transactionSummary)
            .let { return@let disposable::add }
    }
}
