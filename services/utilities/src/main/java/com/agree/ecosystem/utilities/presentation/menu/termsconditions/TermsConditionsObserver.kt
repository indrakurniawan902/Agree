package com.agree.ecosystem.utilities.presentation.menu.termsconditions

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class TermsConditionsObserver(
    private val view: TermsConditionsDataContract,
    private val viewModel: TermsConditionsViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.termsConditions.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetTermsConditionsLoading()
                is VmData.Success -> view.onGetTermsConditionsSuccess(it.data[0].term)
                is VmData.Failure -> view.onGetTermsConditionsFailed(it.throwable)
                is VmData.Empty -> view.onGetTermsConditionsFailed(EmptyException())
            }
        }
    }
}
