package com.agree.ecosystem.monitoring.data.reqres.web

import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitiesSopMissedItem
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.SopActivityDetailBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.detailadditionalactivitysop.ResponseDetailAdditionalActivitySop
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity.PhaseActivityItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.DetailSubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesselindividual.IndividualSubVesselItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface AgreeMonitoringEngineApiClient {

    @PATCH("engine/v1/activities/{id}")
    fun updateDetailActivitySop(
        @Path("id") id: String,
        @Query("field_id") fieldId: String,
        @Body data: SopActivityDetailBodyPost
    ): Flowable<Response<DevApiResponse<SopActivityDetailBodyPost>>>

    @POST("engine/v1/activities")
    fun insertDetailActivitySop(
        @Body data: InsertActivitySopBodyPost
    ): Flowable<Response<DevApiResponse<InsertActivitySopItem>>>

    @GET("/engine/v1/query?")
    fun getDetailSubVessel(
        @Query("key") key: String,
        @Query("filter") filter: String,
        @Query("is_distinct") isDistinct: Boolean
    ): Flowable<Response<DevApiResponse<List<DetailSubVesselItem>>>>

    @GET("/engine/v1/query?")
    fun getListPhaseActivitySop(
        @Query("key") key: String,
        @Query("filter") filter: String,
    ): Flowable<Response<DevApiResponse<List<PhaseActivityItem>>>>

    @GET("/engine/v1/query?")
    fun getListActivitySop(
        @Query("key") key: String,
        @Query("filter") filter: String,
        @Query("pageSize") pageSize: Int,
        @Query("pageNo") pageNo: Int,
    ): Flowable<Response<DevApiResponse<List<ActivitySopItem>>>>

    @GET("/engine/v1/query?")
    fun getListActivitySop(
        @Query("key") key: String,
        @Query("filter") filter: String
    ): Single<Response<DevApiResponse<List<ActivitySopItem>>>>

    @GET("/engine/v1/query?")
    fun getListActivityMissedSop(
        @Query("key") key: String,
        @Query("filter") filter: String
    ): Flowable<Response<DevApiResponse<List<ActivitiesSopMissedItem>>>>

    @GET("/engine/v1/activities")
    fun getDetailAdditionalActivitySop(
        @Query("sortBy") sortBy: String,
        @Query("isDistinct") isDistinct: String,
        @Query("columns") columns: String,
        @Query("filter") filter: String,
        @Query("pageSize") pageSize: Int,
        @Query("pageNo") pageNo: Int,
    ): Flowable<Response<ResponseDetailAdditionalActivitySop>>

    @GET("engine/v1/query?")
    fun getListSubVesselIndividual(
        @Query("key") key: String,
        @Query("filter") filter: String,
        @Query("pageSize") pageSize: Int,
        @Query("pageNo") pageNo: Int,
        @Query("sortBy") sortBy: String = "si.order"
    ): Flowable<Response<DevApiResponse<List<IndividualSubVesselItem>>>>
}
