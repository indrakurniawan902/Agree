package com.agree.ecosystem.smartfarming.data.reqres

import com.agree.ecosystem.smartfarming.data.reqres.model.DeviceItem
import com.agree.ecosystem.smartfarming.data.reqres.model.ParameterTestDataItem
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusItem
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusRequestBody
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryItem
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryQuery
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface SmartFarmingRepository : DevRepository {
    fun getParameterTest(subVesselId: String, schema: String, deviceId: String, date: String?) : Flowable<ParameterTestDataItem>
    fun getDeviceHistoryData(
        period: String,
        date: String,
        query: DeviceHistoryQuery
    ): Flowable<List<DeviceHistoryItem>>

    fun getListDevice(subVesselId: String): Flowable<List<DeviceItem>>

    fun updateReadStatus(body: UpdateReadStatusRequestBody): Flowable<UpdateReadStatusItem>
}
