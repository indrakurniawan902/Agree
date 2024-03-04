package com.agree.ecosystem.monitoring.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.monitoring.data.reqres.db.MonitoringDb
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitiesSopMissedItem
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopEntity
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.category.IncidentCategoryItem
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.DataSopActivityDetail
import com.agree.ecosystem.monitoring.data.reqres.model.detailcompany.DetailCompanyItem
import com.agree.ecosystem.monitoring.data.reqres.model.eventdotcalendar.EventDotCalendarItem
import com.agree.ecosystem.monitoring.data.reqres.model.incident.AddIncidentBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.incident.AddIncidentItem
import com.agree.ecosystem.monitoring.data.reqres.model.incident.IncidentItem
import com.agree.ecosystem.monitoring.data.reqres.model.incident.IncidentParams
import com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment.AddIncidentCommentBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment.IncidentCommentItem
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.UpdateActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselParams
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.DetailSubVesselEntity
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.DetailSubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.InsertSopDateBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.totalactivitysop.TotalActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionDetailItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionParams
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionSummaryItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.insert.InsertTransactionBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.insert.InsertTransactionItem
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.update.UpdateTransactionBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.update.UpdateTransactionItem
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselItem
import com.agree.ecosystem.monitoring.data.reqres.web.AgreeMonitoringApi
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.ValidationActivityDetail
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.devbase.data.utilities.rx.operators.singleApiError
import com.oratakashi.viewbinding.core.network.rxjava2.flowable.networkSyncReverse
import io.reactivex.Flowable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@WorkerThread
class MonitoringDataStore(
    override val dbService: MonitoringDb,
    override val webService: AgreeMonitoringApi
) : MonitoringRepository {

    override fun uploadPhoto(photo: File): Flowable<String> {
        val requestFile = photo.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("photo", photo.name, requestFile)
        return webService.uploadPhoto(body).lift(flowableApiError()).map { it.data }
    }

    override fun getDetailActivitySop(
        id: String,
        date: String,
        subVesselId: String,
        individualId: String?
    ): Flowable<List<DataSopActivityDetail>> {
        return webService.getDetailActivitySop(id, date, subVesselId, individualId)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun insertSopDate(id: InsertSopDateBodyPost): Flowable<Any> {
        return webService.insertSopDate(id).lift(flowableApiError()).map { it.data }
    }

    override fun getListActivity(
        subVesselId: String,
        date: String,
        page: Int,
        quantity: Int
    ): Flowable<List<ActivitySopItem>> {
        return webService.getListActivity(subVesselId, date, page, quantity)
            .lift(flowableApiError()).map { it.data }
    }

    override fun getListActivity(
        subVesselId: String,
        date: String,
        sopRecordType: String?
    ): Flowable<List<ActivitySopEntity>> {
        return networkSyncReverse<List<ActivitySopItem>, List<ActivitySopEntity>>(
            saveToDb = { dbService.monitoringDao().addAllActivitySop(it) },
            fetchDb = {
                dbService.monitoringDao().getAllActivitySop(
                    subVesselId,
                    "${date.replace("'", "")}%",
                    0
                )
            },
            fetchApi = {
                webService.getListActivity(
                    subVesselId,
                    date,
                    sopRecordType
                )
                    .lift(singleApiError())
                    .map { it.data ?: emptyList() }
            },
            mapData = {
                it.map {
                    it.toActivitySopEntity(subVesselId, false)
                }
            }
        )
    }

    override fun getListHistoryActivity(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<List<ActivitySopItem>> {
        return webService.getListHistoryActivity(subVesselId, isCompleted)
            .lift(flowableApiError()).map {
                it.data
            }
    }

    override fun getListHistoryMissedActivity(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<List<ActivitiesSopMissedItem>> {
        return webService.getListHistoryMissedActivity(subVesselId, isCompleted)
            .lift(flowableApiError()).map {
                it.data
            }
    }

    override fun getTotalActivity(subVesselId: String): Flowable<TotalActivitySopItem> {
        return webService.getTotalActivity(subVesselId).lift(flowableApiError()).map { it.data }
    }

    override fun getEventDotCalendar(id: String): Flowable<List<EventDotCalendarItem>> {
        return webService.getEventDotCalendar(id).lift(flowableApiError())
            .map { it.data ?: emptyList() }
    }

    override fun getDetailSubVessel(subVesselId: String): Flowable<DetailSubVesselEntity> {
        return networkSyncReverse<DetailSubVesselItem, DetailSubVesselEntity>(
            saveToDb = { dbService.monitoringDao().addAllDetailSubVessel(listOf(it)) },
            fetchDb = { dbService.monitoringDao().getAllDetailSubVessel() },
            fetchApi = {
                webService.getDetailSubVessel(subVesselId)
                    .lift(singleApiError())
                    .map { it.data!! }
            },
            mapData = { it.toDetailSubVesselEntity() },
        )
    }

    override fun deactivateSubVessel(subVesselId: String): Flowable<DetailSubVesselItem> {
        return webService.deactivateSubVessel(subVesselId).lift(flowableApiError()).map { it.data }
    }

    override fun getListSubVessel(subVesselParams: SubVesselParams): Flowable<ArrayList<SubVesselItem>> {
        return webService.getListSubVessel(
            mutableMapOf(
                "quantity" to subVesselParams.quantity.toString(),
                "page" to subVesselParams.page.toString(),
                "search" to subVesselParams.search,
                "vessel_id" to subVesselParams.companyMemberId,
                "subsector_id" to subVesselParams.subSectorId
            ).apply {
                if (!subVesselParams.hasSmartfarm) {
                    return@apply
                }
                put("has_smartfarm", "true")
            }
        )
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getVessel(id: String): Flowable<VesselItem> {
        return webService.getVessel(
            id
        )
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun addNewIncident(
        data: AddIncidentBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<AddIncidentItem> {
        return webService.addNewIncident(
            data.toMap(), images
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getListIncident(incidentParams: IncidentParams): Flowable<ArrayList<IncidentItem>> {
        return webService.getListIncident(
            incidentParams.page,
            incidentParams.quantity,
            incidentParams.subVesselId
        )
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun addNewAdditionalActivity(data: InsertActivitySopBodyPost): Flowable<InsertActivitySopItem> {
        return webService.addNewAdditionalActivity(data).lift(flowableApiError()).map { it.data }
    }

    override fun getListIncidentComment(activityId: String): Flowable<ArrayList<IncidentCommentItem>> {
        return webService.getListIncidentComment(activityId).lift(flowableApiError())
            .map { it.data }
    }

    override fun addIncidentComment(data: AddIncidentCommentBodyPost): Flowable<IncidentCommentItem> {
        return webService.addIncidentComment(data).lift(flowableApiError()).map { it.data }
    }

    override fun getListTransaction(param: TransactionParams): Flowable<List<TransactionItem>> {
        return webService.getListTransaction(
            param.page,
            param.quantity,
            param.query.toQueryString()
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getTransactionSummary(subVesselId: String): Flowable<TransactionSummaryItem> {
        return webService.getTransactionSummary(subVesselId).lift(flowableApiError())
            .map { it.data }
    }

    override fun getIncidentCategories(
        companyId: String,
        commodityId: String
    ): Flowable<List<IncidentCategoryItem>> {
        return webService.getIncidentCategories(companyId, commodityId).lift(flowableApiError())
            .map { it.data }
    }

    override fun insertTransaction(
        data: InsertTransactionBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<InsertTransactionItem> {
        return webService.insertTransaction(
            data.toMap(), images
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getTransactionDetail(id: String): Flowable<TransactionDetailItem> {
        return webService.getTransactionDetail(id).lift(flowableApiError()).map { it.data }
    }

    override fun getEntryPoint(
        subVesselId: String
    ): Flowable<DataSopActivityDetail> {
        return webService.getEntryPoint(subVesselId).lift(flowableApiError()).map { it.data }
    }

    override fun getValidationActivityDetail(subVesselId: String): Flowable<ValidationActivityDetail> {
        return webService.getValidationActivityDetail(subVesselId).lift(flowableApiError())
            .map { it.data }
    }

    override fun updateTransaction(
        id: String,
        data: UpdateTransactionBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<UpdateTransactionItem> {
        return webService.updateTransaction(
            id, data.toMap(), images
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getCompanyDetail(companyId: String): Flowable<DetailCompanyItem> {
        return webService.getCompanyDetail(companyId).lift(flowableApiError()).map { it.data }
    }

    override fun updateDetailActivitySop(
        subvesselId: String,
        data: UpdateActivitySopBodyPost
    ): Flowable<UpdateActivitySopBodyPost> {
        return webService.updateDetailActivitySop(subvesselId, data).lift(flowableApiError())
            .map { it.data }
    }
}
