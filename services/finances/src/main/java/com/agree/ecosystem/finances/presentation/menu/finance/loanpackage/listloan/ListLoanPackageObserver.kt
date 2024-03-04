package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.devbase.data.source.VmData
import timber.log.Timber

class ListLoanPackageObserver(
    private val view: ListLoanPackageDataContract,
    private val viewModel: ListLoanPackageViewModel
) : DefaultLifecycleObserver {


    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Timber.tag("LIFEE").v("ONRESUME LIST")
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.lists.observe(owner, {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.listLoanPackageOnLoading()
                is VmData.Success -> view.listLoanPackageOnSuccess(it.data)
                is VmData.Failure -> view.listLoanPackageOnError(it.throwable)
                is VmData.Empty -> view.listLoanPackageOnEmpty()
            }
        })
    }
}
