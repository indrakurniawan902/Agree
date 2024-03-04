package com.agree.ecosystem.auth.presentation.menu.verification

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class VerificationObserver(
    private val view: VerificationDataContract,
    private val viewModel: VerificationViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.verifyOption.observe(owner) {
            when (it) {
                is VmData.Default -> view.onVerificationIdle()
                is VmData.Loading -> view.onVerificationLoading()
                is VmData.Success -> view.onVerificationSuccess(it.data)
                is VmData.Failure -> view.onVerificationFailed(it.throwable)
                is VmData.Empty -> view.onVerificationFailed(EmptyException())
            }
        }
    }
}
