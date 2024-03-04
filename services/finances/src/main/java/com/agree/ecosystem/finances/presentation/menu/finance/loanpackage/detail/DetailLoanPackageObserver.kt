package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class DetailLoanPackageObserver(
    private val contract: DetailLoanPackgeDataContract,
    private val viewModel: DetailLoanPackageViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.data.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> contract.detailLoanPackageOnLoading()
                is VmData.Success -> contract.detailLoanPackageOnSuccess(it.data)
                is VmData.Failure -> contract.detailLoanPackageOnError(it.throwable)
                else -> {
                    Unit
                }
            }
        }
    }
}
