package com.agree.ecosystem.auth.presentation.menu.changepassword

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class ChangePasswordObserver(
    private val view: ChangePasswordDataContract,
    private val viewModel: ChangePasswordViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.changePassword.observe(owner) {
            when (it) {
                is VmData.Loading -> view.onLoading()
                is VmData.Success -> view.onChangePasswordSuccess()
                is VmData.Failure -> view.onChangePasswordFailed(it.throwable)
                is VmData.Empty -> view.onChangePasswordFailed(EmptyException())
                is VmData.Default -> Unit
            }
        }
    }
}
