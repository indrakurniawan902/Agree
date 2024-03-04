package com.agree.ecosystem.smartfarming.data.reqres.web

import com.agree.ecosystem.smartfarming.data.reqres.model.DeviceItem
import com.agree.ecosystem.smartfarming.data.reqres.model.ParameterTestDataItem
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusItem
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusRequestBody
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SmartFarmingApiClient {
    @GET("integrations/v1/smartfarm/last-metrics/{subvessel_id}")
    fun getParameterTest(
        @Path("subvessel_id") subVesselId : String,
        @Query("schema") schema : String = "user",
        @Query("smartfarm_partner_device_id") deviceId : String,
        @Query("date") date : String?
    ): Flowable<Response<DevApiResponse<ParameterTestDataItem>>>

    @GET("integrations/v1/smartfarm/device/history")
    fun getDeviceHistoryData(
        @Query("period") period: String,
        @Query("date") date: String,
        @Query("query") query: String
    ): Flowable<Response<DevApiResponse<List<DeviceHistoryItem>>>>

    @GET("integrations/v1/smartfarm/device/subvessel/{subvessel_id}")
    fun getListDevice(
        @Path("subvessel_id") subVesselId : String
    ): Flowable<Response<DevApiResponse<List<DeviceItem>>>>

    @PUT("integrations/v1/smartfarm/status/metrics")
    fun updateReadStatus(
        @Body body: UpdateReadStatusRequestBody
    ): Flowable<Response<DevApiResponse<UpdateReadStatusItem>>>
}