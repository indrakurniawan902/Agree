package com.agree.ecosystem.finances.presentation.menu.finance.loanactive

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class ListLoanActiveObserver(
    private val contract: ListLoanActiveDataContract,
    private val viewModel: ListLoanActiveViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.lists.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> contract.listLoanActiveOnLoading()
                is VmData.Success -> {
                    contract.listLoanActiveOnSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> contract.listLoanActiveOnError(it.throwable)
                is VmData.Empty -> contract.listLoanActiveOnEmpty(listOf())
            }
        }

        viewModel.loadMoreLists.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> contract.loadMoreListLoanActiveOnLoading()
                is VmData.Success -> {
                    contract.loadMoreListLoanActiveOnSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> contract.loadMoreListLoanActiveOnError(it.throwable)
                is VmData.Empty -> contract.loadMoreListLoanActiveOnEmpty(listOf())
            }
        }
    }
}
