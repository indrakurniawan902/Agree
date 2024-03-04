package com.agree.ecosystem.auth.presentation.base.activity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class AuthObserver(
    private val view: AuthDataContract,
    private val viewModel: AuthViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.logout.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> Unit
                is VmData.Success -> view.onLogoutSuccess()
                is VmData.Failure -> Unit
                is VmData.Empty -> Unit
            }
        }
    }
}
