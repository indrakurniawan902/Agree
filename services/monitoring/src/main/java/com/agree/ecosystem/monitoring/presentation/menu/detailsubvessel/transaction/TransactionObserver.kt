package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class TransactionObserver(
    private val view: TransactionDataContract,
    private val viewModel: TransactionViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.transactions.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetTransactionsLoading()
                is VmData.Success -> {
                    view.onGetTransactionsSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onGetTransactionsFailed(it.throwable)
                is VmData.Empty -> view.onTransactionsEmpty()
            }
        }

        viewModel.loadMoreTransactions.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreLoading()
                is VmData.Success -> {
                    view.onLoadMoreSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onLoadMoreFailed()
                is VmData.Empty -> view.onLoadMoreEmpty()
            }
        }

        viewModel.transactionSummary.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onTransactionSummaryLoading()
                is VmData.Success -> view.onTransactionSummarySuccess(it.data)
                is VmData.Failure -> view.onTransactionSummaryFailed(it.throwable)
                is VmData.Empty -> view.onTransactionSummaryFailed(EmptyException())
            }
        }
    }
}
