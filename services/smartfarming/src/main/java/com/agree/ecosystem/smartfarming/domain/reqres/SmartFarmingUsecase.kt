package com.agree.ecosystem.smartfarming.domain.reqres

import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusRequestBody
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryQuery
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestData
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.UpdateReadStatus
import com.agree.ecosystem.smartfarming.domain.reqres.model.devicehistory.DeviceHistory
import com.agree.ecosystem.smartfarming.domain.reqres.model.tools.Device
import io.reactivex.Flowable

interface SmartFarmingUsecase {
    fun getParameterTest(subVesselId: String, schema: String, deviceId: String, date: String?) : Flowable<ParameterTestData>
    fun getDeviceHistoryData(period: String, date: String, query: DeviceHistoryQuery): Flowable<List<DeviceHistory>>
    fun getListDevice(subVesselId: String): Flowable<List<Device>>
    fun updateReadStatus(body: UpdateReadStatusRequestBody): Flowable<UpdateReadStatus>
}