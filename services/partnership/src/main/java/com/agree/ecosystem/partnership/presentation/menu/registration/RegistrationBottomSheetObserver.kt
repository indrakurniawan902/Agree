package com.agree.ecosystem.partnership.presentation.menu.registration

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class RegistrationBottomSheetObserver(
    private val view: RegistrationBottomSheetDataContract,
    private val viewModel: RegistrationViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.registration.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onRegistrationLoading()
                is VmData.Success -> {
                    view.onRegistrationSuccess(it.data)
                }
                is VmData.Failure -> view.onRegistrationFailed(it.throwable)
                is VmData.Empty -> view.onRegistrationEmpty()
            }
        }
    }
}
