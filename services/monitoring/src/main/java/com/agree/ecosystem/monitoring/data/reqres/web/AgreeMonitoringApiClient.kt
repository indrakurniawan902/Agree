package com.agree.ecosystem.monitoring.data.reqres.web

import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitiesSopMissedItem
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.category.IncidentCategoryItem
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.DataSopActivityDetail
import com.agree.ecosystem.monitoring.data.reqres.model.detailcompany.DetailCompanyItem
import com.agree.ecosystem.monitoring.data.reqres.model.eventdotcalendar.EventDotCalendarItem
import com.agree.ecosystem.monitoring.data.reqres.model.incident.AddIncidentItem
import com.agree.ecosystem.monitoring.data.reqres.model.incident.IncidentItem
import com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment.AddIncidentCommentBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment.IncidentCommentItem
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.UpdateActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.DetailSubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.InsertSopDateBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.totalactivitysop.TotalActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionDetailItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionSummaryItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.insert.InsertTransactionItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.update.UpdateTransactionItem
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselItem
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.ValidationActivityDetail
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AgreeMonitoringApiClient {

    @GET("activities/v1/activities/detail-form")
    fun getDetailActivitySop(
        @Query("id") id: String,
        @Query("date") date: String,
        @Query("subvessel_id") subVesselId: String,
        @Query("individual_id") individualId: String?
    ): Flowable<Response<DevApiResponse<List<DataSopActivityDetail>>>>

    @Multipart
    @PUT("activities/v1/activities/upload-photo")
    fun uploadPhoto(
        @Part photo: MultipartBody.Part
    ): Flowable<Response<DevApiResponse<String>>>

    @POST("activities/v1/activities/sop-date")
    fun insertSopDate(
        @Body data: InsertSopDateBodyPost
    ): Flowable<Response<DevApiResponse<Any>>>

    @GET("activities/v1/activities/monitoring")
    fun getListActivity(
        @Query("subvessel_id") subVesselId: String,
        @Query("date") date: String,
        @Query("page") page: Int,
        @Query("quantity") quantity: Int
    ): Flowable<Response<DevApiResponse<List<ActivitySopItem>>>>

    @GET("activities/v1/activities/monitoring")
    fun getListActivity(
        @Query("subvessel_id") subVesselId: String,
        @Query("date") date: String,
        @Query("sop_record_type") sopRecordType: String?
    ): Single<Response<DevApiResponse<List<ActivitySopItem>>>>

    @GET("activities/v1/activities/monitoring")
    fun getListHistoryActivity(
        @Query("subvessel_id") subVesselId: String,
        @Query("is_completed") isCompleted: Boolean
    ): Flowable<Response<DevApiResponse<List<ActivitySopItem>>>>

    @GET("activities/v1/activities/monitoring")
    fun getListHistoryMissedActivity(
        @Query("subvessel_id") subVesselId: String,
        @Query("is_completed") isCompleted: Boolean,
        @Query("group_by") groupBy: String = "date"
    ): Flowable<Response<DevApiResponse<List<ActivitiesSopMissedItem>>>>

    @GET("activities/v1/activities/total-monitoring")
    fun getTotalActivity(
        @Query("subvessel_id") subVesselId: String
    ): Flowable<Response<DevApiResponse<TotalActivitySopItem>>>

    @GET("activities/v1/activities/calender-info")
    fun getEventDotCalendar(
        @Query("id") id: String
    ): Flowable<Response<DevApiResponse<List<EventDotCalendarItem>?>>>

    @GET("/activities/v1/subvessels/{subvessel_id}")
    fun getDetailSubVessel(
        @Path("subvessel_id") subVesselId: String,
    ): Single<Response<DevApiResponse<DetailSubVesselItem>>>

    @PUT("/activities/v1/subvessels/deactivate/{subvessel_id}")
    fun deactivateSubVessel(
        @Path("subvessel_id") subVesselId: String,
    ): Flowable<Response<DevApiResponse<DetailSubVesselItem>>>

    @GET("activities/v1/subvessels")
    fun getListSubVesselSmartfarming(
        @Query("quantity") quantity: Int,
        @Query("page") page: Int,
        @Query("search") search: String,
        @Query("vessel_id") companyMemberId: String,
        @Query("subsector_id") subSectorId: String,
        @Query("has_smartfarm") hasSmartfarm: Boolean,
    ): Flowable<Response<DevApiResponse<ArrayList<SubVesselItem>>>>

    @GET("activities/v1/subvessels")
    fun getListSubVessel(
        @QueryMap params: Map<String, String?>
    ): Flowable<Response<DevApiResponse<ArrayList<SubVesselItem>>>>

    @GET("activities/v1/vessels/{id}")
    fun getVessel(
        @Path("id") id: String
    ): Flowable<Response<DevApiResponse<VesselItem>>>

    @Multipart
    @POST("activities/v1/activity-events")
    fun addNewIncident(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part images: List<MultipartBody.Part>
    ): Flowable<Response<DevApiResponse<AddIncidentItem>>>

    @GET("activities/v1/activity-events")
    fun getListIncident(
        @Query("page") page: Int,
        @Query("quantity") quantity: Int,
        @Query("subvessel_id") subVesselId: String,
    ): Flowable<Response<DevApiResponse<ArrayList<IncidentItem>>>>

    @POST("activities/v1/activities")
    fun addNewAdditionalActivity(
        @Body data: InsertActivitySopBodyPost
    ): Flowable<Response<DevApiResponse<InsertActivitySopItem>>>

    //    @GET("https://server.digitaltelkom.dev/agree-superapp/activities/v1/incident-comment")
    @GET("activities/v1/activity-events/comment/{activity_id}?page=1&quantity=9999")
    fun getListIncidentComment(
        @Path("activity_id") activityId: String
    ): Flowable<Response<DevApiResponse<ArrayList<IncidentCommentItem>>>>

    //    @POST("activities/v1/incident-comment")
    @POST("activities/v1/activity-events/comment")
    fun addIncidentComment(
        @Body data: AddIncidentCommentBodyPost
    ): Flowable<Response<DevApiResponse<IncidentCommentItem>>>

    @GET("activities/v1/transactions")
//    @GET("https://private-e3386-agreesuperapps.apiary-mock.com/transaction")
    fun getListTransaction(
        @Query("page") page: Int,
        @Query("quantity") quantity: Int,
        @Query("query") query: String,
    ): Flowable<Response<DevApiResponse<List<TransactionItem>>>>

    @Multipart
    @POST("activities/v1/transaction")
    fun insertTransaction(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part images: List<MultipartBody.Part>
    ): Flowable<Response<DevApiResponse<InsertTransactionItem>>>

    @GET("activities/v1/transactions/summary")
//    @GET("https://private-e3386-agreesuperapps.apiary-mock.com/transaction/summary")
    fun getTransactionSummary(
        @Query("subvessel_id") subVesselId: String
    ): Flowable<Response<DevApiResponse<TransactionSummaryItem>>>

    @GET("partners/v1/companies/incident-category/{company_id}")
    fun getIncidentCategories(
        @Path("company_id") companyId: String,
        @Query("commodityId") commodityId: String,
    ): Flowable<Response<DevApiResponse<List<IncidentCategoryItem>>>>

    @GET("activities/v1/transaction/{id}")
    fun getTransactionDetail(
        @Path("id") id: String
    ): Flowable<Response<DevApiResponse<TransactionDetailItem>>>

    @GET("activities/v1/entry-point/{id}")
    fun getEntryPoint(
        @Path("id") id: String
    ): Flowable<Response<DevApiResponse<DataSopActivityDetail>>>

    @Multipart
    @PUT("activities/v1/transaction/{id}")
    fun updateTransaction(
        @Path("id") id: String,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part images: List<MultipartBody.Part>
    ): Flowable<Response<DevApiResponse<UpdateTransactionItem>>>

    @GET("api/v1/agree/company/{companyId}")
    fun getCompanyDetail(
        @Path("companyId") companyId: String
    ): Flowable<Response<DevApiResponse<DetailCompanyItem>>>

    @PUT("activities/v1/subvessels/entry-point/{subvessel_id}")
    fun updateDetailActivitySop(
        @Path("subvessel_id") subvesselId: String,
        @Body data: UpdateActivitySopBodyPost
    ): Flowable<Response<DevApiResponse<UpdateActivitySopBodyPost>>>

    @GET("activities/v1/subvessels/entry-point/status/{subvessel_id}")
    fun getValidationActivityDetail(
        @Path("subvessel_id") subVesselId: String
    ): Flowable<Response<DevApiResponse<ValidationActivityDetail>>>
}
