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
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class AgreeMonitoringApi(
    private val api: AgreeMonitoringApiClient,
) : AgreeMonitoringApiClient, WebService {

    override fun uploadPhoto(photo: MultipartBody.Part): Flowable<Response<DevApiResponse<String>>> {
        return api.uploadPhoto(photo)
    }

    override fun getDetailActivitySop(
        id: String,
        date: String,
        subVesselId: String,
        individualId: String?
    ): Flowable<Response<DevApiResponse<List<DataSopActivityDetail>>>> {
        return api.getDetailActivitySop(id, date, subVesselId, individualId)
    }

    override fun insertSopDate(data: InsertSopDateBodyPost): Flowable<Response<DevApiResponse<Any>>> {
        return api.insertSopDate(data)
    }

    override fun getListActivity(
        subVesselId: String,
        date: String,
        page: Int,
        quantity: Int
    ): Flowable<Response<DevApiResponse<List<ActivitySopItem>>>> {
        return api.getListActivity(
            subVesselId, date, page, quantity
        )
    }

    override fun getListActivity(
        subVesselId: String,
        date: String,
        sopRecordType: String?
    ): Single<Response<DevApiResponse<List<ActivitySopItem>>>> {
        return api.getListActivity(
            subVesselId, date, sopRecordType
        )
    }

    override fun getListHistoryActivity(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<Response<DevApiResponse<List<ActivitySopItem>>>> {
        return api.getListHistoryActivity(
            subVesselId, isCompleted
        )
    }

    override fun getListHistoryMissedActivity(
        subVesselId: String,
        isCompleted: Boolean,
        groupBy: String
    ): Flowable<Response<DevApiResponse<List<ActivitiesSopMissedItem>>>> {
        return api.getListHistoryMissedActivity(
            subVesselId, isCompleted
        )
    }

    override fun getTotalActivity(subVesselId: String): Flowable<Response<DevApiResponse<TotalActivitySopItem>>> {
        return api.getTotalActivity(
            subVesselId
        )
    }

    override fun getEventDotCalendar(id: String): Flowable<Response<DevApiResponse<List<EventDotCalendarItem>?>>> {
        return api.getEventDotCalendar(id)
    }

    override fun getDetailSubVessel(subVesselId: String): Single<Response<DevApiResponse<DetailSubVesselItem>>> {
        return api.getDetailSubVessel(subVesselId)
    }

    override fun deactivateSubVessel(subVesselId: String): Flowable<Response<DevApiResponse<DetailSubVesselItem>>> {
        return api.deactivateSubVessel(subVesselId)
    }

    override fun getListSubVesselSmartfarming(
        quantity: Int,
        page: Int,
        search: String,
        companyMemberId: String,
        subSectorId: String,
        hasSmartfarm: Boolean
    ): Flowable<Response<DevApiResponse<ArrayList<SubVesselItem>>>> {
        return api.getListSubVesselSmartfarming(
            quantity,
            page,
            search,
            companyMemberId,
            subSectorId,
            hasSmartfarm
        )
    }

    override fun getListSubVessel(params: Map<String, String?>): Flowable<Response<DevApiResponse<ArrayList<SubVesselItem>>>> {
        return api.getListSubVessel(params)
    }

    override fun getVessel(id: String): Flowable<Response<DevApiResponse<VesselItem>>> {
        return api.getVessel(id)
    }

    override fun addNewIncident(
        params: Map<String, RequestBody>,
        images: List<MultipartBody.Part>
    ): Flowable<Response<DevApiResponse<AddIncidentItem>>> {
        return api.addNewIncident(params, images)
    }

    override fun getListIncident(
        page: Int,
        quantity: Int,
        subVesselId: String
    ): Flowable<Response<DevApiResponse<ArrayList<IncidentItem>>>> {
        return api.getListIncident(
            page,
            quantity,
            subVesselId
        )
    }

    override fun addNewAdditionalActivity(data: InsertActivitySopBodyPost): Flowable<Response<DevApiResponse<InsertActivitySopItem>>> {
        return api.addNewAdditionalActivity(data)
    }

    override fun getListIncidentComment(activityId: String): Flowable<Response<DevApiResponse<ArrayList<IncidentCommentItem>>>> {
        return api.getListIncidentComment(activityId)
    }

    override fun addIncidentComment(data: AddIncidentCommentBodyPost): Flowable<Response<DevApiResponse<IncidentCommentItem>>> {
        return api.addIncidentComment(data)
    }

    override fun getListTransaction(
        page: Int,
        quantity: Int,
        query: String
    ): Flowable<Response<DevApiResponse<List<TransactionItem>>>> {
        return api.getListTransaction(page, quantity, query)
    }

    override fun getTransactionSummary(subVesselId: String): Flowable<Response<DevApiResponse<TransactionSummaryItem>>> {
        return api.getTransactionSummary(subVesselId)
    }

    override fun getIncidentCategories(
        companyId: String,
        commodityId: String
    ): Flowable<Response<DevApiResponse<List<IncidentCategoryItem>>>> {
        return api.getIncidentCategories(companyId, commodityId)
    }

    override fun insertTransaction(
        data: Map<String, RequestBody>,
        images: List<MultipartBody.Part>
    ): Flowable<Response<DevApiResponse<InsertTransactionItem>>> {
        return api.insertTransaction(data, images)
    }

    override fun getTransactionDetail(id: String): Flowable<Response<DevApiResponse<TransactionDetailItem>>> {
        return api.getTransactionDetail(id)
    }

    override fun getEntryPoint(
        id: String
    ): Flowable<Response<DevApiResponse<DataSopActivityDetail>>> {
        return api.getEntryPoint(id)
    }

    override fun getValidationActivityDetail(subVesselId: String): Flowable<Response<DevApiResponse<ValidationActivityDetail>>> {
        return api.getValidationActivityDetail(subVesselId)
    }

    override fun updateTransaction(
        id: String,
        data: Map<String, RequestBody>,
        images: List<MultipartBody.Part>
    ): Flowable<Response<DevApiResponse<UpdateTransactionItem>>> {
        return api.updateTransaction(id, data, images)
    }

    override fun getCompanyDetail(companyId: String): Flowable<Response<DevApiResponse<DetailCompanyItem>>> {
        return api.getCompanyDetail(companyId)
    }

    override fun updateDetailActivitySop(
        subvesselId: String,
        data: UpdateActivitySopBodyPost
    ): Flowable<Response<DevApiResponse<UpdateActivitySopBodyPost>>> {
        return api.updateDetailActivitySop(subvesselId, data)
    }
}
