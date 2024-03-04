package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.insertupdate

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class InsertUpdateTransactionObserver(
    private val view: InsertUpdateTransactionDataContract,
    private val viewModel: InsertUpdateTransactionViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.insertTransaction.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onInsertTransactionLoading()
                is VmData.Success -> view.onInsertTransactionSuccess()
                is VmData.Failure -> view.onInsertTransactionFailed(it.throwable)
                is VmData.Empty -> view.onInsertTransactionFailed(EmptyException())
            }
        }

        viewModel.transactionDetail.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onTransactionDetailLoading()
                is VmData.Success -> view.onGetTransactionDetailSuccess(it.data)
                is VmData.Failure -> view.onTransactionDetailFailed(it.throwable)
                is VmData.Empty -> view.onTransactionDetailFailed(EmptyException())
            }
        }

        viewModel.updateTransaction.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onUpdateTransactionLoading()
                is VmData.Success -> view.onUpdateTransactionSuccess()
                is VmData.Failure -> view.onUpdateTransactionFailed(it.throwable)
                is VmData.Empty -> view.onUpdateTransactionFailed(EmptyException())
            }
        }

        viewModel.productType.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetProductTypeLoading()
                is VmData.Success -> view.onGetProductTypeSuccess(it.data)
                is VmData.Failure -> view.onGetProductTypeFailed(it.throwable)
                is VmData.Empty -> view.onGetProductTypeFailed(EmptyException())
            }
        }
    }
}
