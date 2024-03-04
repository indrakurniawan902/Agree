package com.agree.ecosystem.monitoring.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.monitoring.data.reqres.db.MonitoringDb
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitiesSopMissedItem
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopEntity
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.SopActivityDetailBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity.PhaseActivityItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.DetailSubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesselindividual.IndividualSubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.web.AgreeMonitoringEngineApi
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParams
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParamsPagination
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.UpdateSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVesselParams
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.devbase.data.utilities.rx.operators.singleApiError
import com.oratakashi.viewbinding.core.network.rxjava2.flowable.networkSyncReverse
import io.reactivex.Flowable

@WorkerThread
class CrudEngineDataStore(
    override val dbService: MonitoringDb,
    override val webService: AgreeMonitoringEngineApi
) : CrudEngineRepository {

    override fun getListActivitySop(params: CrudEngineParams): Flowable<List<ActivitySopEntity>> {
        return networkSyncReverse <List<ActivitySopItem>, List<ActivitySopEntity>>(
            saveToDb = { dbService.monitoringDao().addAllActivitySop(it) },
            fetchDb = {
                dbService.monitoringDao().getAllActivitySop(
                    params.subVesselId,
                    "${params.date}%",
                    1
                )
            },
            fetchApi = {
                webService.getListActivitySop(
                    key = params.key,
                    filter = params.filter
                ).lift(singleApiError()).map { it.data ?: emptyList() }
            },
            mapData = {
                it.map {
                    it.toActivitySopEntity(params.subVesselId, true)
                }
            }
        )
    }

    override fun updateDetailActivity(params: UpdateSopActivityDetailParams): Flowable<SopActivityDetailBodyPost> {
        return webService.updateDetailActivitySop(
            id = params.id,
            fieldId = params.fieldId,
            data = params.data
        ).lift(flowableApiError()).map { it.data }
    }

    override fun insertDetailActivity(data: InsertActivitySopBodyPost): Flowable<InsertActivitySopItem> {
        return webService.insertDetailActivitySop(data).lift(flowableApiError()).map { it.data }
    }

    override fun getDetailSubVessel(params: DetailSubVesselParams): Flowable<List<DetailSubVesselItem>> {
        return webService.getDetailSubVessel(
            key = params.key,
            filter = params.filter,
            isDistinct = params.isDistinct
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getListPhaseActivitySop(params: CrudEngineParams): Flowable<List<PhaseActivityItem>> {
        return webService.getListPhaseActivitySop(
            key = params.key,
            filter = params.filter
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getListActivitySop(params: CrudEngineParamsPagination): Flowable<List<ActivitySopItem>> {
        return webService.getListActivitySop(
            key = params.key,
            filter = params.filter,
            pageSize = params.pageSize,
            pageNo = params.pageNo
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getListActivityMissedSop(params: CrudEngineParamsPagination): Flowable<List<ActivitiesSopMissedItem>> {
        return webService.getListActivityMissedSop(
            key = params.key,
            filter = params.filter
        ).lift(flowableApiError()).map { it.data }
    }

    override fun getDetailAdditionalActivitySop(params: AdditionalSopActivityDetailParams): Flowable<List<AdditionalSopActivityDetail>> {
        return webService.getDetailAdditionalActivitySop(
            sortBy = params.sortBy,
            isDistinct = params.isDistinct,
            columns = params.columns,
            filter = params.filter,
            pageSize = params.pageSize,
            pageNo = params.pageNo
        ).lift(flowableApiError()).map { it.toAdditionalSopActivityDetail() }
    }

    override fun getListIndividualSubVessel(params: CrudEngineParamsPagination): Flowable<List<IndividualSubVesselItem>> {
        return webService.getListSubVesselIndividual(
            key = params.key,
            filter = params.filter,
            pageSize = params.pageSize,
            pageNo = params.pageNo
        ).lift(flowableApiError()).map { it.data }
    }
}
