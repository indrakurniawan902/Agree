package com.agree.ecosystem.common.data.reqres.web

import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

class AgreeMonitoringApi(
    private val api: AgreeMonitoringApiClient,
) : AgreeMonitoringApiClient, WebService {

    override fun getListSubVesselSmartfarming(
        quantity: Int,
        page: Int,
        search: String,
        companyMemberId: String,
        subSectorId: String,
        userId: String,
        hasSmartfarm: Boolean
    ): Flowable<Response<DevApiResponse<List<SubVesselItem>>>> {
        return api.getListSubVesselSmartfarming(
            quantity,
            page,
            search,
            companyMemberId,
            subSectorId,
            userId,
            hasSmartfarm
        )
    }

    override fun getListSubVessel(params: Map<String, String?>): Single<Response<DevApiResponse<List<SubVesselItem>>>> {
        return api.getListSubVessel(params)
    }

    override fun getListVessel(
        page: Int,
        quantity: Int,
        vesselName: String,
        userId: String,
        status: String,
        subSectorId: String
    ): Single<Response<DevApiResponse<List<VesselItem>>>> {
        return api.getListVessel(page, quantity, vesselName, userId, status, subSectorId)
    }
}
