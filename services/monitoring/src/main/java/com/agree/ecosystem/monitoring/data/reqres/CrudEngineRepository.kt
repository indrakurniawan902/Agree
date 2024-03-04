package com.agree.ecosystem.monitoring.data.reqres

import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitiesSopMissedItem
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopEntity
import com.agree.ecosystem.monitoring.data.reqres.model.activitysop.ActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.SopActivityDetailBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopBodyPost
import com.agree.ecosystem.monitoring.data.reqres.model.insertactivitysop.InsertActivitySopItem
import com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity.PhaseActivityItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesseldetails.DetailSubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.subvesselindividual.IndividualSubVesselItem
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParams
import com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams.CrudEngineParamsPagination
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.UpdateSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetailParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVesselParams
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface CrudEngineRepository : DevRepository {
    fun updateDetailActivity(params: UpdateSopActivityDetailParams): Flowable<SopActivityDetailBodyPost>
    fun insertDetailActivity(data: InsertActivitySopBodyPost): Flowable<InsertActivitySopItem>
    fun getListActivitySop(params: CrudEngineParamsPagination): Flowable<List<ActivitySopItem>>
    fun getListActivityMissedSop(params: CrudEngineParamsPagination): Flowable<List<ActivitiesSopMissedItem>>
    fun getListActivitySop(params: CrudEngineParams): Flowable<List<ActivitySopEntity>>
    fun getDetailSubVessel(params: DetailSubVesselParams): Flowable<List<DetailSubVesselItem>>
    fun getListPhaseActivitySop(params: CrudEngineParams): Flowable<List<PhaseActivityItem>>
    fun getDetailAdditionalActivitySop(params: AdditionalSopActivityDetailParams): Flowable<List<AdditionalSopActivityDetail>>
    fun getListIndividualSubVessel(params: CrudEngineParamsPagination): Flowable<List<IndividualSubVesselItem>>
}
