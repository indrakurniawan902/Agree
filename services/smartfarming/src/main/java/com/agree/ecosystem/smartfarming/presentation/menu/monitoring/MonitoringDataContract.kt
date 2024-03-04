package com.agree.ecosystem.smartfarming.presentation.menu.monitoring

import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestData
import com.agree.ecosystem.smartfarming.domain.reqres.model.devicehistory.DeviceHistory
import com.agree.ecosystem.smartfarming.domain.reqres.model.tools.Device

interface MonitoringDataContract {

    fun fetchTestParameters(date: String? = null)

    fun fetchLastUpdate()

    fun fetchDeviceHistory()

    fun onGetTestParametersLoading()

    fun onGetTestParametersSuccess(data: ParameterTestData)

    fun onGetTestParametersError(t: Throwable?)

    fun onGetLastUpdateLoading()

    fun onGetLastUpdateSuccess()

    fun onGetLastUpdateError(t: Throwable?)

    fun onGetDeviceHistoryLoading()

    fun onGetDeviceHistorySuccess(data: List<DeviceHistory>)

    fun onGetDeviceHistoryError(t: Throwable?)

    fun onFetchListDevice()

    fun onGetListDeviceLoading()

    fun onGetListDeviceSuccess(data: List<Device>)

    fun onGetListDeviceError(t: Throwable?)

    fun showTimePickerDialog(data: List<String>)

    fun fetchUpdateReadStatus()

    fun onUpdateReadStatusLoading()

    fun onUpdateReadStatusSuccess()

    fun onUpdateReadStatusError(t: Throwable?)
}