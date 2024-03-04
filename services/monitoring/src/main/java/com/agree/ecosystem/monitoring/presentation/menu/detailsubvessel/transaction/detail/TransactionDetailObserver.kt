package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class TransactionDetailObserver(
    private val view: TransactionDetailDataContract,
    private val viewModel: TransactionDetailViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.transactionDetail.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onTransactionDetailLoading()
                is VmData.Success -> view.onGetTransactionDetailSuccess(it.data)
                is VmData.Failure -> view.onTransactionDetailFailed(it.throwable)
                is VmData.Empty -> view.onTransactionDetailFailed(EmptyException())
            }
        }
    }
}
