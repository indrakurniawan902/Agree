package com.agree.ecosystem.auth.presentation.menu.verification

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class OtpObserver(
    private val view: OtpDataContract,
    private val viewModel: OtpViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.getOtp.observe(owner) {
            when (it) {
                is VmData.Default -> view.onOtpIdle()
                is VmData.Loading -> view.onOtpLoading()
                is VmData.Success -> {
                    view.onOtpSuccess()
                }
                is VmData.Failure -> view.onOtpFailed(it.throwable)
                is VmData.Empty -> view.onOtpFailed(EmptyException())
            }
        }
    }
}
