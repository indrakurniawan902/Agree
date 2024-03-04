package com.agree.ecosystem.auth.presentation.menu.login

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class LoginObserver(
    private val view: LoginDataContract,
    private val viewModel: LoginViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.login.observe(owner) {
            when (it) {
                is VmData.Default -> view.onLoginIdle()
                is VmData.Loading -> view.onLoginLoading()
                is VmData.Success -> view.onLoginSuccess(it.data)
                is VmData.Failure -> view.onLoginFailed(it.throwable)
                is VmData.Empty -> view.onLoginFailed(EmptyException())
            }
        }
    }
}
