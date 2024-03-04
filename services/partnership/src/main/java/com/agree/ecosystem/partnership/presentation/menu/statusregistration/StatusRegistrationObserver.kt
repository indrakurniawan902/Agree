package com.agree.ecosystem.partnership.presentation.menu.statusregistration

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class StatusRegistrationObserver(
    private val view: StatusRegistrationDataContract,
    private val viewModel: StatusRegistrationViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.registrationStatusList.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetRegistrationStatusListLoading()
                is VmData.Success -> {
                    view.onGetRegistrationStatusListSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onGetRegistrationStatusListFailed(it.throwable)
                is VmData.Empty -> view.onRegistrationStatusListEmpty()
            }
        }
        viewModel.loadMoreRegistrationStatusList.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreLoading()
                is VmData.Success -> {
                    viewModel.page++
                    view.onLoadMoreSuccess(it.data)
                }
                is VmData.Failure -> view.onLoadMoreFailed()
                is VmData.Empty -> view.onLoadMoreEmpty()
            }
        }
    }
}
