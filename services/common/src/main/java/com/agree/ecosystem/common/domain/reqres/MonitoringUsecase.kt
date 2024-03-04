package com.agree.ecosystem.common.domain.reqres

import com.agree.ecosystem.common.data.reqres.model.monitoring.SubVesselParams
import com.agree.ecosystem.common.data.reqres.model.monitoring.VesselParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import io.reactivex.Flowable

interface MonitoringUsecase {
    fun getListSubVessel(subVesselParams: SubVesselParams): Flowable<List<SubVessel>>
    fun getListVessel(vesselParams: VesselParams): Flowable<List<Vessel>>
}
