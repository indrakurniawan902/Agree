package com.agree.ecosystem.smartfarming.data.reqres.web

import com.agree.ecosystem.smartfarming.data.reqres.model.DeviceItem
import com.agree.ecosystem.smartfarming.data.reqres.model.ParameterTestDataItem
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusItem
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusRequestBody
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response

class SmartFarmingApi (private val api : SmartFarmingApiClient) : SmartFarmingApiClient, WebService {
    override fun getParameterTest(
        subVesselId: String,
        schema: String,
        deviceId: String,
        date: String?
    ): Flowable<Response<DevApiResponse<ParameterTestDataItem>>> {
       return api.getParameterTest(subVesselId, schema, deviceId, date)
    }

    override fun getDeviceHistoryData(
        period: String,
        date: String,
        query: String
    ): Flowable<Response<DevApiResponse<List<DeviceHistoryItem>>>> {
        return api.getDeviceHistoryData(period, date, query)
    }

    override fun getListDevice(subVesselId: String): Flowable<Response<DevApiResponse<List<DeviceItem>>>> {
        return api.getListDevice(subVesselId)
    }

    override fun updateReadStatus(body: UpdateReadStatusRequestBody): Flowable<Response<DevApiResponse<UpdateReadStatusItem>>> {
        return api.updateReadStatus(body)
    }
}