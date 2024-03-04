package com.agree.ecosystem.auth.presentation.menu.register

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class RegisterObserver(
    private val view: RegisterDataContract,
    private val viewModel: RegisterViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.register.observe(owner) {
            when (it) {
                is VmData.Default -> view.onRegisterIdle()
                is VmData.Loading -> view.onRegisterLoading()
                is VmData.Success -> view.onRegisterSuccess()
                is VmData.Failure -> view.onRegisterFailed(it.throwable)
                is VmData.Empty -> view.onRegisterFailed(EmptyException())
            }
        }
    }
}
