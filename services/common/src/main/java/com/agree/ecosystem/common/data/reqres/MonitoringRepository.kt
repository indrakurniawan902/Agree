package com.agree.ecosystem.common.data.reqres

import com.agree.ecosystem.common.data.reqres.model.monitoring.SubVesselParams
import com.agree.ecosystem.common.data.reqres.model.monitoring.VesselParams
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselEntity
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselEntity
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface MonitoringRepository : DevRepository {
    fun getListVessel(vesselparams: VesselParams): Flowable<List<VesselEntity>>
    fun getListSubVessel(subVesselParams: SubVesselParams): Flowable<List<SubVesselEntity>>
}
