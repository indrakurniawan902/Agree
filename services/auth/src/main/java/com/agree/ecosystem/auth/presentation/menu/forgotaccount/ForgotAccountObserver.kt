package com.agree.ecosystem.auth.presentation.menu.forgotaccount

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class ForgotAccountObserver(
    private val view: ForgotAccountDataContract,
    private val viewModel: ForgotAccountViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.forgotAccountEmail.observe(owner) {
            when (it) {
                is VmData.Default -> view.onForgotAccountIdle()
                is VmData.Loading -> view.onForgotAccountLoading()
                is VmData.Success -> view.onForgotAccountSuccess()
                is VmData.Failure -> view.onForgotAccountFailed(it.throwable)
                is VmData.Empty -> view.onForgotAccountFailed(EmptyException())
            }
        }
        viewModel.forgotAccountPhoneNumber.observe(owner) {
            when (it) {
                is VmData.Default -> view.onForgotAccountIdle()
                is VmData.Loading -> view.onForgotAccountLoading()
                is VmData.Success -> view.onForgotAccountSuccess()
                is VmData.Failure -> view.onForgotAccountFailed(it.throwable)
                is VmData.Empty -> view.onForgotAccountFailed(EmptyException())
            }
        }
    }
}
