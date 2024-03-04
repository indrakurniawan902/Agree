package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.detail

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.monitoring.domain.reqres.MonitoringUseCase
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionDetail
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class TransactionDetailViewModel(
    private val usecase: MonitoringUseCase,
) : DevViewModel() {
    private val _transactionDetail = DevData<TransactionDetail>().apply { default() }
    val transactionDetail = _transactionDetail.immutable()

    fun getTransactionDetail(id: String) {
        usecase.getTransactionDetail(id)
            .setHandler(_transactionDetail)
            .let { return@let disposable::add }
    }
}
