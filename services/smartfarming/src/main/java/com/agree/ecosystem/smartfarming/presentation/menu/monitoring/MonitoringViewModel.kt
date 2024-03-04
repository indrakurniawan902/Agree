package com.agree.ecosystem.smartfarming.presentation.menu.monitoring

import androidx.lifecycle.MutableLiveData
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusRequestBody
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryQuery
import com.agree.ecosystem.smartfarming.domain.reqres.SmartFarmingUsecase
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestData
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.UpdateReadStatus
import com.agree.ecosystem.smartfarming.domain.reqres.model.devicehistory.DeviceHistory
import com.agree.ecosystem.smartfarming.domain.reqres.model.tools.Device
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class MonitoringViewModel(
    private val usecase: SmartFarmingUsecase
) : DevViewModel() {

    private val _parameterTestData = DevData<ParameterTestData>().apply { default() }
    val parameterTestData = _parameterTestData.immutable()

    private val _listDevice = DevData<List<Device>>().apply { default() }
    val listDevice = _listDevice.immutable()

    private val _selectedDevice = MutableLiveData<Device>()
    val selectedDevice = _selectedDevice.immutable()

    fun getParameterTest(subvesselId: String,schema: String, deviceId: String, date: String? = null) {
        usecase.getParameterTest(subvesselId, schema, deviceId, date)
            .setHandler(_parameterTestData)
        let { return@let disposable::add }
    }

    fun getListDevice(subVesselId: String){
        usecase.getListDevice(subVesselId)
            .setHandler(_listDevice)
        let { return@let disposable::add }
    }

    private val _deviceHistoryData = DevData<List<DeviceHistory>>().apply { default() }
    val deviceHistoryData = _deviceHistoryData.immutable()

    fun getDeviceHistoryData(date: String, query: DeviceHistoryQuery) {
        usecase.getDeviceHistoryData("month", date, query)
            .setHandler(_deviceHistoryData)
            .let { return@let disposable::add }
    }

    fun setSelectedDevice(selectedDevice: Device){
        _selectedDevice.value = selectedDevice
    }

    private val _updateReadStatus = DevData<UpdateReadStatus>().apply { default() }
    val updateReadStatus = _updateReadStatus.immutable()

    fun updateReadStatus(body: UpdateReadStatusRequestBody) {
        usecase.updateReadStatus(body)
            .setHandler(_updateReadStatus)
        let { return@let disposable::add }
    }
}
