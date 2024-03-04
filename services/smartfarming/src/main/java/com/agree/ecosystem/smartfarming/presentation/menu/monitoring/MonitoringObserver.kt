package com.agree.ecosystem.smartfarming.presentation.menu.monitoring

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class MonitoringObserver(
    private val view: MonitoringDataContract,
    private val viewModel: MonitoringViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.parameterTestData.observe(owner) {
            when(it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetTestParametersLoading()
                is VmData.Success -> {
                    view.onGetTestParametersSuccess(it.data)
                }
                is VmData.Failure -> view.onGetTestParametersError(it.throwable)
                is VmData.Empty -> Unit
            }
        }

        viewModel.listDevice.observe(owner){
            when(it){
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetListDeviceLoading()
                is VmData.Success -> {
                    view.onGetListDeviceSuccess(it.data)
                }
                is VmData.Failure -> view.onGetListDeviceError(it.throwable)
                is VmData.Empty -> Unit
            }
        }

        viewModel.deviceHistoryData.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetDeviceHistoryLoading()
                is VmData.Success -> view.onGetDeviceHistorySuccess(it.data)
                is VmData.Failure -> view.onGetDeviceHistoryError(it.throwable)
                is VmData.Empty -> view.onGetDeviceHistoryError(EmptyException())
            }
        }

        viewModel.updateReadStatus.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onUpdateReadStatusLoading()
                is VmData.Success -> view.onUpdateReadStatusSuccess()
                is VmData.Failure -> view.onUpdateReadStatusError(it.throwable)
                is VmData.Empty -> Unit
            }
        }
    }
}