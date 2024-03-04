package com.agree.ecosystem.monitoring.data.reqres

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
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.ValidationActivityDetail
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable
import okhttp3.MultipartBody
import java.io.File

interface MonitoringRepository : DevRepository {
    fun uploadPhoto(photo: File): Flowable<String>
    fun getDetailActivitySop(
        id: String,
        date: String,
        subVesselId: String,
        individualId: String?
    ): Flowable<List<DataSopActivityDetail>>

    fun insertSopDate(id: InsertSopDateBodyPost): Flowable<Any>
    fun getListActivity(
        subVesselId: String,
        date: String,
        page: Int,
        quantity: Int
    ): Flowable<List<ActivitySopItem>>

    fun getListActivity(
        subVesselId: String,
        date: String,
        sopRecordType: String?
    ): Flowable<List<ActivitySopEntity>>

    fun getListHistoryActivity(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<List<ActivitySopItem>>

    fun getListHistoryMissedActivity(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<List<ActivitiesSopMissedItem>>

    fun getTotalActivity(subVesselId: String): Flowable<TotalActivitySopItem>
    fun getEventDotCalendar(id: String): Flowable<List<EventDotCalendarItem>>
    fun getDetailSubVessel(subVesselId: String): Flowable<DetailSubVesselEntity>
    fun deactivateSubVessel(subVesselId: String): Flowable<DetailSubVesselItem>
    fun getListSubVessel(subVesselParams: SubVesselParams): Flowable<ArrayList<SubVesselItem>>
    fun getVessel(id: String): Flowable<VesselItem>
    fun getListIncident(incidentParams: IncidentParams): Flowable<ArrayList<IncidentItem>>
    fun addNewIncident(
        data: AddIncidentBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<AddIncidentItem>

    fun addNewAdditionalActivity(data: InsertActivitySopBodyPost): Flowable<InsertActivitySopItem>
    fun getListIncidentComment(activityId: String): Flowable<ArrayList<IncidentCommentItem>>
    fun addIncidentComment(data: AddIncidentCommentBodyPost): Flowable<IncidentCommentItem>
    fun getListTransaction(param: TransactionParams): Flowable<List<TransactionItem>>
    fun insertTransaction(
        data: InsertTransactionBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<InsertTransactionItem>

    fun getTransactionSummary(subVesselId: String): Flowable<TransactionSummaryItem>
    fun getIncidentCategories(
        companyId: String,
        commodityId: String
    ): Flowable<List<IncidentCategoryItem>>

    fun getTransactionDetail(id: String): Flowable<TransactionDetailItem>
    fun getEntryPoint(subVesselId: String): Flowable<DataSopActivityDetail>
    fun getValidationActivityDetail(subVesselId: String): Flowable<ValidationActivityDetail>
    fun updateTransaction(
        id: String,
        data: UpdateTransactionBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<UpdateTransactionItem>

    fun getCompanyDetail(companyId: String): Flowable<DetailCompanyItem>

    fun updateDetailActivitySop(
        subvesselId: String,
        data: UpdateActivitySopBodyPost
    ): Flowable<UpdateActivitySopBodyPost>
}
