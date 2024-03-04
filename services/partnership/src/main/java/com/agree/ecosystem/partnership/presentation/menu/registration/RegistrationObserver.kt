package com.agree.ecosystem.partnership.presentation.menu.registration

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class RegistrationObserver(
    private val view: RegistrationDataContract,
    private val viewModel: RegistrationViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.unitType.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetUnitTypeLoading()
                is VmData.Success -> {
                    view.onGetUnitTypeSuccess(it.data)
                }
                is VmData.Failure -> view.onGetUnitTypeFailed(it.throwable)
                is VmData.Empty -> view.onGetUnitTypeEmpty()
            }
        }
        viewModel.validation.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onValidationLoading()
                is VmData.Success -> {
                    view.onValidationSuccess(it.data)
                }
                is VmData.Failure -> view.onValidationFailed(it.throwable)
                is VmData.Empty -> view.onValidationEmpty()
            }
        }
    }
}
