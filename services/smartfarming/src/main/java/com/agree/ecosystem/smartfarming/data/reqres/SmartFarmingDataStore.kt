package com.agree.ecosystem.smartfarming.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.smartfarming.data.reqres.model.DeviceItem
import com.agree.ecosystem.smartfarming.data.reqres.model.ParameterTestDataItem
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusItem
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusRequestBody
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryItem
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryQuery
import com.agree.ecosystem.smartfarming.data.reqres.web.SmartFarmingApi
import com.devbase.data.source.db.DbService
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.google.gson.Gson
import io.reactivex.Flowable

@WorkerThread
class SmartFarmingDataStore (
    override val dbService: DbService? = null,
    override val webService: SmartFarmingApi
    ) : SmartFarmingRepository {
    override fun getParameterTest(
        subVesselId: String,
        schema: String,
        deviceId: String,
        date: String?
    ): Flowable<ParameterTestDataItem> {
        return webService.getParameterTest(subVesselId = subVesselId, schema = schema, deviceId = deviceId, date = date)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getDeviceHistoryData(
        period: String,
        date: String,
        query: DeviceHistoryQuery
    ): Flowable<List<DeviceHistoryItem>> {
        val queryString = Gson().toJson(query)
        return webService.getDeviceHistoryData(period, date, queryString)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getListDevice(subVesselId: String): Flowable<List<DeviceItem>> {
        return webService.getListDevice(subVesselId)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun updateReadStatus(body: UpdateReadStatusRequestBody): Flowable<UpdateReadStatusItem> {
        return webService.updateReadStatus(body)
            .lift(flowableApiError())
            .map { it.data }
    }
}