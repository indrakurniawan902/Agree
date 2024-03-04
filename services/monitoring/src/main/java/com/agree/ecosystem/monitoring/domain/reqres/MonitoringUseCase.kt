package com.agree.ecosystem.monitoring.domain.reqres

import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.SopActivityDetailBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.incident.AddIncidentBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.incident.IncidentParams
import com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment.AddIncidentCommentBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.UpdateActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselParams
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.InsertSopDateBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.TransactionParams
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.insert.InsertTransactionBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.transaction.update.UpdateTransactionBodyPost
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitiesSopMissed
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySopGroupByDate
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.EventDateActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.category.IncidentCategory
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParams
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParamsPagination
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.InsertActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.UpdateSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.ValidationActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.detailcompany.DetailCompany
import com.agree.ecosystem.monitoring.domain.reqres.model.eventdotcalendar.EventDotCalendar
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.AddIncident
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.Incident
import com.agree.ecosystem.monitoring.domain.reqres.model.incidentcomment.IncidentComment
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVesselParams
import com.agree.ecosystem.monitoring.domain.reqres.model.totalactivitysop.TotalActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.Transaction
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.TransactionSummary
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.insert.InsertTransaction
import com.agree.ecosystem.monitoring.domain.reqres.model.transaction.update.UpdateTransaction
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import io.reactivex.Flowable
import okhttp3.MultipartBody
import java.io.File

interface MonitoringUseCase {
    fun getActivity(
        subVesselId: String,
        date: String,
        page: Int,
        quantity: Int
    ): Flowable<List<ActivitySop>>

    fun getActivity(
        subVesselId: String,
        date: String,
        sopRecordType: String?
    ): Flowable<List<ActivitySop>>

    fun getActivityDetailSop(
        id: String,
        date: String,
        subVesselId: String,
        individualId: String?
    ): Flowable<List<SopActivityDetail>>

    fun getDetailSubVessel(id: String): Flowable<DetailSubVessel>
    fun deactivateSubVessel(subVesselId: String): Flowable<DetailSubVessel>
    fun getEventDotCalendar(id: String): Flowable<List<EventDotCalendar>>
    fun getListActivityGroupByDate(
        subVesselId: String,
        date: String,
        page: Int,
        quantity: Int
    ): Flowable<List<ActivitySopGroupByDate>>

    fun getListHistoryActivityGroupByDate(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<List<ActivitySopGroupByDate>>

    fun getListHistoryMissedActivityGroupByDate(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<List<ActivitiesSopMissed>>

    fun getTotalActivity(subVesselId: String): Flowable<TotalActivitySop>
    fun updateDetailActivity(params: UpdateSopActivityDetailParams): Flowable<SopActivityDetailBodyPost>
    fun uploadPhoto(photo: File): Flowable<String>
    fun insertTransaction(
        data: InsertTransactionBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<InsertTransaction>

    fun updateTransaction(
        id: String,
        data: UpdateTransactionBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<UpdateTransaction>

    fun getDetailCompany(companyId: String): Flowable<DetailCompany>

    /**
     * UseCase for Crude Engine
     */
    fun getActivityEngine(params: CrudEngineParams): Flowable<List<ActivitySop>>
    fun getActivityEngine(params: CrudEngineParamsPagination): Flowable<List<ActivitySop>>
    fun getActivityGroupByDateCrudeEngine(params: CrudEngineParamsPagination): Flowable<List<ActivitySopGroupByDate>>
    fun getActivityMissedGroupByDateCrudeEngine(params: CrudEngineParamsPagination): Flowable<List<ActivitiesSopMissed>>
    fun getDetailAdditionalActivitySop(params: AdditionalSopActivityDetailParams): Flowable<List<AdditionalSopActivityDetail>>
    fun getDetailSubVesselCrudeEngine(params: DetailSubVesselParams): Flowable<DetailSubVessel>
    fun getListEventDateActivitySop(params: CrudEngineParams): Flowable<List<EventDateActivitySop>>
    fun getListPhaseActivity(params: CrudEngineParams): Flowable<List<PhaseActivity>>
    fun insertDetailActivity(data: InsertActivitySopBodyPost): Flowable<InsertActivitySop>
    fun insertSopDate(data: InsertSopDateBodyPost): Flowable<Any>
    fun getListSubVessel(subVesselParams: SubVesselParams): Flowable<ArrayList<SubVessel>>
    fun getVessel(id: String): Flowable<Vessel>
    fun getListIncident(incidentParams: IncidentParams): Flowable<ArrayList<Incident>>
    fun addNewIncident(
        data: AddIncidentBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<AddIncident>

    fun addNewAdditionalActivity(data: InsertActivitySopBodyPost): Flowable<InsertActivitySop>
    fun getListIncidentComment(activityId: String): Flowable<List<IncidentComment>>
    fun addIncidentComment(data: AddIncidentCommentBodyPost): Flowable<IncidentComment>
    fun getListTransaction(param: TransactionParams): Flowable<List<Transaction>>
    fun getTransactionSummary(subVesselId: String): Flowable<TransactionSummary>
    fun getIncidentCategories(
        companyId: String,
        commodityId: String
    ): Flowable<List<IncidentCategory>>

    fun getTransactionDetail(id: String): Flowable<TransactionDetail>
    fun getIndividualSubVessel(params: CrudEngineParamsPagination): Flowable<List<IndividualSubVessel>>
    fun getEntryPoint(subVesselId: String): Flowable<SopActivityDetail>
    fun getValidationActivityDetail(subVesselId: String): Flowable<ValidationActivityDetail>

    fun updateActivityDetailSop(
        subvesselId: String,
        data: UpdateActivitySopBodyPost
    ): Flowable<String>
}
