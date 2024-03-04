package com.agree.ecosystem.common.data.reqres.web

import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AgreeMonitoringApiClient {
    @GET("activities/v1/subvessels")
    fun getListSubVesselSmartfarming(
        @Query("quantity") quantity: Int,
        @Query("page") page: Int,
        @Query("search") search: String,
        @Query("company_member_id") companyMemberId: String,
        @Query("subsector_id") subSectorId: String = "",
        @Query("user_id") userId: String,
        @Query("has_smartfarm") hasSmartfarm: Boolean
    ): Flowable<Response<DevApiResponse<List<SubVesselItem>>>>

    @GET("activities/v1/subvessels")
    fun getListSubVessel(
//        @Query("quantity") quantity: Int,
//        @Query("page") page: Int,
//        @Query("search") search: String,
//        @Query("company_member_id") companyMemberId: String,
//        @Query("subsector_id") subSectorId: String = "",
//        @Query("user_id") userId: String
        @QueryMap params: Map<String, String?>
    ): Single<Response<DevApiResponse<List<SubVesselItem>>>>

    @GET("activities/v1/vessels")
    fun getListVessel(
        @Query("page") page: Int,
        @Query("quantity") quantity: Int,
        @Query("vessel_name") vesselName: String,
        @Query("user_id") userId: String,
        @Query("status") status: String,
        @Query("subsector_id") subSectorId: String
    ): Single<Response<DevApiResponse<List<VesselItem>>>>
}
