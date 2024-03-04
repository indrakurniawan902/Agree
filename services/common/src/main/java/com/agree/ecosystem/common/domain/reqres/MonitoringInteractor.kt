package com.agree.ecosystem.common.domain.reqres

import com.agree.ecosystem.common.data.reqres.MonitoringRepository
import com.agree.ecosystem.common.data.reqres.model.monitoring.SubVesselParams
import com.agree.ecosystem.common.data.reqres.model.monitoring.VesselParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import io.reactivex.Flowable

class MonitoringInteractor(
    private val repo: MonitoringRepository,
) : MonitoringUsecase {

    override fun getListSubVessel(subVesselParams: SubVesselParams): Flowable<List<SubVessel>> {
        return repo.getListSubVessel(subVesselParams).map {
            it.map { subVesselItem ->
                subVesselItem.toSubVessel()
            }
        }
    }

    override fun getListVessel(vesselParams: VesselParams): Flowable<List<Vessel>> {
        return repo.getListVessel(vesselParams).map {
            it.map { vesselItem ->
                vesselItem.toVessel()
            }
        }
    }
}
