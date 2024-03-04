package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.cultivator

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan.LoanSubmissionViewModel
import com.devbase.data.source.VmData
import timber.log.Timber

class LoanSubmissionCultivatorObserver(
    private val contract: LoanSubmissionCultivatorDataContract,
    private val viewModel: LoanSubmissionViewModel
) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        Timber.tag("LIFE").v("ONSTART")
        super.onStart(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        Timber.tag("LIFE").v("ONRESUME")
        super.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        Timber.tag("LIFE").v("ONPAUSE")
        super.onPause(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        Timber.tag("LIFE").v("ONSTOP")
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Timber.tag("LIFE").v("ONDESTROY")
        super.onDestroy(owner)
    }

    override fun onCreate(owner: LifecycleOwner) {
        Timber.tag("LIFE").v("ONCREATE")
        super.onCreate(owner)
        viewModel.datas.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> contract.checkMemberEligibilityOnLoading()
                is VmData.Success -> contract.checkMemberEligibilityOnSuccess(it.data)
                is VmData.Failure -> contract.checkMemberEligibilityOnError(it.throwable)
                is VmData.Empty -> contract.checkMemberEligibilityOnEmpty(listOf())
            }
        }
    }
}
