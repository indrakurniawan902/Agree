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
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

class AgreeMonitoringEngineApi(
    private val api: AgreeMonitoringEngineApiClient
) : AgreeMonitoringEngineApiClient, WebService {

    override fun updateDetailActivitySop(
        id: String,
        fieldId: String,
        data: SopActivityDetailBodyPost
    ): Flowable<Response<DevApiResponse<SopActivityDetailBodyPost>>> {
        return api.updateDetailActivitySop(id, fieldId, data)
    }

    override fun insertDetailActivitySop(data: InsertActivitySopBodyPost): Flowable<Response<DevApiResponse<InsertActivitySopItem>>> {
        return api.insertDetailActivitySop(data)
    }

    override fun getDetailSubVessel(
        key: String,
        filter: String,
        isDistinct: Boolean
    ): Flowable<Response<DevApiResponse<List<DetailSubVesselItem>>>> {
        return api.getDetailSubVessel(key, filter, isDistinct)
    }

    override fun getListPhaseActivitySop(
        key: String,
        filter: String
    ): Flowable<Response<DevApiResponse<List<PhaseActivityItem>>>> {
        return api.getListPhaseActivitySop(key, filter)
    }

    override fun getListActivitySop(
        key: String,
        filter: String,
        pageSize: Int,
        pageNo: Int
    ): Flowable<Response<DevApiResponse<List<ActivitySopItem>>>> {
        return api.getListActivitySop(key, filter, pageSize, pageNo)
    }

    override fun getListActivitySop(
        key: String,
        filter: String
    ): Single<Response<DevApiResponse<List<ActivitySopItem>>>> {
        return api.getListActivitySop(key, filter)
    }

    override fun getListActivityMissedSop(
        key: String,
        filter: String
    ): Flowable<Response<DevApiResponse<List<ActivitiesSopMissedItem>>>> {
        return api.getListActivityMissedSop(key, filter)
    }

    override fun getDetailAdditionalActivitySop(
        sortBy: String,
        isDistinct: String,
        columns: String,
        filter: String,
        pageSize: Int,
        pageNo: Int
    ): Flowable<Response<ResponseDetailAdditionalActivitySop>> {
        return api.getDetailAdditionalActivitySop(
            sortBy,
            isDistinct,
            columns,
            filter,
            pageSize,
            pageNo
        )
    }

    override fun getListSubVesselIndividual(
        key: String,
        filter: String,
        pageSize: Int,
        pageNo: Int,
        sortBy: String
    ): Flowable<Response<DevApiResponse<List<IndividualSubVesselItem>>>> {
        return api.getListSubVesselIndividual(key, filter, pageSize, pageNo)
    }
}
