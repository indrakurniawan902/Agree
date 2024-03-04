package com.agree.ecosystem.monitoring.domain.reqres

import com.agree.ecosystem.monitoring.data.reqres.CrudEngineRepository
import com.agree.ecosystem.monitoring.data.reqres.MonitoringRepository
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

class MonitoringInteractor(
    val engineRepo: CrudEngineRepository,
    val repo: MonitoringRepository
) : MonitoringUseCase {

    override fun getActivity(
        subVesselId: String,
        date: String,
        page: Int,
        quantity: Int
    ): Flowable<List<ActivitySop>> {
        return repo.getListActivity(subVesselId, date, page, quantity)
            .map { it.map { data -> data.toActivitySop() } }
    }

    override fun getActivity(
        subVesselId: String,
        date: String,
        sopRecordType: String?
    ): Flowable<List<ActivitySop>> {
        return repo.getListActivity(subVesselId, date, sopRecordType)
            .map { it.map { data -> data.toActivitySop() } }
    }

    override fun getActivityDetailSop(
        id: String,
        date: String,
        subVesselId: String,
        individualId: String?
    ): Flowable<List<SopActivityDetail>> {
        return repo.getDetailActivitySop(id, date, subVesselId, individualId).map {
            it.map { sopDetailItem ->
                sopDetailItem.toSopActivityDetail()
            }
        }
    }

    override fun getDetailSubVessel(id: String): Flowable<DetailSubVessel> {
        return repo.getDetailSubVessel(id).map {
            it.toDetailSubVessel()
        }
    }

    override fun deactivateSubVessel(subVesselId: String): Flowable<DetailSubVessel> {
        return repo.deactivateSubVessel(subVesselId).map {
            it.toDetailSubVessel()
        }
    }

    override fun getEventDotCalendar(id: String): Flowable<List<EventDotCalendar>> {
        return repo.getEventDotCalendar(id).map { it.map { data -> data.toEventDotCalendar() } }
    }

    override fun getListActivityGroupByDate(
        subVesselId: String,
        date: String,
        page: Int,
        quantity: Int
    ): Flowable<List<ActivitySopGroupByDate>> {
        val listActivity =
            repo.getListActivity(subVesselId, date, page, quantity)
                .map { it.map { data -> data.toActivitySop() } }
        val listActivityGroupByDate = listActivity.map {
            it.groupBy { data -> data.date }
                .map { data -> ActivitySopGroupByDate(data.key, data.value) }
        }
        return listActivityGroupByDate
    }

    override fun getListHistoryActivityGroupByDate(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<List<ActivitySopGroupByDate>> {
        val listActivity =
            repo.getListHistoryActivity(subVesselId, isCompleted)
                .map { it.map { data -> data.toActivitySop() } }
        val listActivityGroupByDate = listActivity.map {
            it.groupBy { data -> data.date }
                .map { data -> ActivitySopGroupByDate(data.key, data.value) }
        }
        return listActivityGroupByDate
    }

    override fun getListHistoryMissedActivityGroupByDate(
        subVesselId: String,
        isCompleted: Boolean
    ): Flowable<List<ActivitiesSopMissed>> {
        return repo.getListHistoryMissedActivity(subVesselId, isCompleted)
            .map { it.map { data -> data.toActivitiesSopMissed() } }
    }

    override fun getTotalActivity(subVesselId: String): Flowable<TotalActivitySop> {
        return repo.getTotalActivity(subVesselId).map { it.toTotalActivity() }
    }

    override fun updateDetailActivity(params: UpdateSopActivityDetailParams): Flowable<SopActivityDetailBodyPost> {
        return engineRepo.updateDetailActivity(params).map { it }
    }

    override fun uploadPhoto(photo: File): Flowable<String> {
        return repo.uploadPhoto(photo).map { it }
    }

    override fun insertTransaction(
        data: InsertTransactionBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<InsertTransaction> {
        return repo.insertTransaction(data, images).map { it.toInsertTransaction() }
    }

    override fun updateTransaction(
        id: String,
        data: UpdateTransactionBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<UpdateTransaction> {
        return repo.updateTransaction(id, data, images).map { it.toUpdateTransaction() }
    }

    override fun getDetailCompany(companyId: String): Flowable<DetailCompany> {
        return repo.getCompanyDetail(companyId).map {
            it.toDetailCompanyItem()
        }
    }

    /**
     * UseCase for Crude Engine
     */
    override fun getActivityEngine(params: CrudEngineParams): Flowable<List<ActivitySop>> {
        return engineRepo.getListActivitySop(params).map { it.map { data -> data.toActivitySop() } }
    }

    override fun getActivityEngine(params: CrudEngineParamsPagination): Flowable<List<ActivitySop>> {
        return engineRepo.getListActivitySop(params).map { it.map { data -> data.toActivitySop() } }
    }

    override fun getListSubVessel(subVesselParams: SubVesselParams): Flowable<ArrayList<SubVessel>> {
        return repo.getListSubVessel(subVesselParams).map {
            val data = arrayListOf<SubVessel>()
            it.map { i ->
                data.add(i.toSubVessel())
            }
            return@map data
        }
    }

    override fun getVessel(id: String): Flowable<Vessel> {
        return repo.getVessel(id).map {
            it.toVessel()
        }
    }

    override fun addNewIncident(
        data: AddIncidentBodyPost,
        images: List<MultipartBody.Part>
    ): Flowable<AddIncident> {
        return repo.addNewIncident(data, images).map { it.toAddIncident() }
    }

    override fun getListIncident(incidentParams: IncidentParams): Flowable<ArrayList<Incident>> {
        return repo.getListIncident(incidentParams).map {
            val data = arrayListOf<Incident>()
            it.map { i ->
                data.add(i.toIncident())
            }
            return@map data
        }
    }

    override fun getActivityGroupByDateCrudeEngine(params: CrudEngineParamsPagination): Flowable<List<ActivitySopGroupByDate>> {
        val listActivitySop =
            engineRepo.getListActivitySop(params).map { it.map { data -> data.toActivitySop() } }
        val listCustomActivitySop = listActivitySop.map {
            it.groupBy { data -> data.date }
                .map { data -> ActivitySopGroupByDate(data.key, data.value) }
        }
        return listCustomActivitySop
    }

    override fun getActivityMissedGroupByDateCrudeEngine(params: CrudEngineParamsPagination): Flowable<List<ActivitiesSopMissed>> {
        return engineRepo.getListActivityMissedSop(params)
            .map { it.map { data -> data.toActivitiesSopMissed() } }
    }

    override fun getDetailAdditionalActivitySop(params: AdditionalSopActivityDetailParams): Flowable<List<AdditionalSopActivityDetail>> {
        return engineRepo.getDetailAdditionalActivitySop(params)
    }

    override fun getDetailSubVesselCrudeEngine(params: DetailSubVesselParams): Flowable<DetailSubVessel> {
        return engineRepo.getDetailSubVessel(params).map {
            it.get(0).toDetailSubVessel()
        }
    }

    override fun getListEventDateActivitySop(params: CrudEngineParams): Flowable<List<EventDateActivitySop>> {
        val listActivitySop =
            engineRepo.getListActivitySop(params).map { it.map { data -> data.toActivitySop() } }
        val listEventDateActivitySop = listActivitySop.map {
            it.groupBy { dataActivitySop -> dataActivitySop.date }.map { dataEventDateActivitySop ->
                EventDateActivitySop(
                    dataEventDateActivitySop.key,
                    !dataEventDateActivitySop.value.groupBy { data -> data.isCompleted }
                        .containsKey(false)
                )
            }
        }
        return listEventDateActivitySop
    }

    override fun getListPhaseActivity(params: CrudEngineParams): Flowable<List<PhaseActivity>> {
        return engineRepo.getListPhaseActivitySop(params)
            .map { it.map { data -> data.toPhaseActivity() } }
    }

    override fun insertDetailActivity(data: InsertActivitySopBodyPost): Flowable<InsertActivitySop> {
        return engineRepo.insertDetailActivity(data).map { it.toInsertActivitySop() }
    }

    override fun addNewAdditionalActivity(data: InsertActivitySopBodyPost): Flowable<InsertActivitySop> {
        return repo.addNewAdditionalActivity(data).map { it.toInsertActivitySop() }
    }

    override fun insertSopDate(data: InsertSopDateBodyPost): Flowable<Any> {
        return repo.insertSopDate(data)
    }

    override fun getListIncidentComment(activityId: String): Flowable<List<IncidentComment>> {
        return repo.getListIncidentComment(activityId)
            .map { it.map { data -> data.toIncidentComment() } }
    }

    override fun addIncidentComment(data: AddIncidentCommentBodyPost): Flowable<IncidentComment> {
        return repo.addIncidentComment(data).map { it.toIncidentComment() }
    }

    override fun getListTransaction(param: TransactionParams): Flowable<List<Transaction>> {
        return repo.getListTransaction(param).map { data ->
            data.map { it.toTransaction() }
        }
    }

    override fun getTransactionSummary(subVesselId: String): Flowable<TransactionSummary> {
        return repo.getTransactionSummary(subVesselId).map { it.toTransactionSummary() }
    }

    override fun getIncidentCategories(
        companyId: String,
        commodityId: String
    ): Flowable<List<IncidentCategory>> {
        return repo.getIncidentCategories(companyId, commodityId)
            .map { it.map { data -> data.toIncidentCategory() } }
    }

    override fun getTransactionDetail(id: String): Flowable<TransactionDetail> {
        return repo.getTransactionDetail(id).map { it.toTransactionDetail() }
    }

    override fun getIndividualSubVessel(params: CrudEngineParamsPagination): Flowable<List<IndividualSubVessel>> {
        return engineRepo.getListIndividualSubVessel(params)
            .map { it.map { data -> data.toIndividualSubVessel() } }
    }

    override fun getEntryPoint(
        subVesselId: String
    ): Flowable<SopActivityDetail> {
        return repo.getEntryPoint(subVesselId).map { it.toSopActivityDetail() }
    }

    override fun getValidationActivityDetail(subVesselId: String): Flowable<ValidationActivityDetail> {
        return repo.getValidationActivityDetail(subVesselId)
    }

    override fun updateActivityDetailSop(
        subvesselId: String,
        data: UpdateActivitySopBodyPost
    ): Flowable<String> {
        return repo.updateDetailActivitySop(subvesselId, data).map { it.data }
    }
}
