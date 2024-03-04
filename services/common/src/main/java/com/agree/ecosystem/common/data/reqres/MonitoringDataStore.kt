package com.agree.ecosystem.common.data.reqres

import androidx.annotation.WorkerThread
import androidx.sqlite.db.SimpleSQLiteQuery
import com.agree.ecosystem.common.data.reqres.model.monitoring.SubVesselParams
import com.agree.ecosystem.common.data.reqres.model.monitoring.VesselParams
import com.agree.ecosystem.common.data.reqres.web.AgreeMonitoringApi
import com.agree.ecosystem.monitoring.data.reqres.db.MonitoringDb
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselEntity
import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselEntity
import com.agree.ecosystem.monitoring.data.reqres.model.vessel.VesselItem
import com.devbase.data.utilities.rx.operators.singleApiError
import com.oratakashi.viewbinding.core.network.rxjava2.flowable.networkSyncReverse
import io.reactivex.Flowable

@WorkerThread
class MonitoringDataStore(
    override val dbService: MonitoringDb,
    override val webService: AgreeMonitoringApi,
) : MonitoringRepository {

    override fun getListSubVessel(
        subVesselParams: SubVesselParams,
    ): Flowable<List<SubVesselEntity>> {
        return networkSyncReverse <List<SubVesselItem>, List<SubVesselEntity>>(
            saveToDb = { dbService.monitoringDao().addAllSubVessel(it) },
            fetchDb = {
                var queryString = "SELECT * FROM subvesselentity"

                /**
                 * Construct Query
                 */
                if (subVesselParams.search.isNotEmpty()) {
                    queryString += " WHERE subVesselName LIKE '%${subVesselParams.search}%'"
                }

                queryString += " LIMIT ${subVesselParams.quantity} OFFSET" +
                    " (${subVesselParams.quantity} * (${subVesselParams.page} - 1))"

                queryString += ";"

                dbService.monitoringDao().getSubVessel(SimpleSQLiteQuery(queryString))
            },
            fetchApi = {
                webService.getListSubVessel(
                    mutableMapOf(
                        "quantity" to subVesselParams.quantity.toString(),
                        "page" to subVesselParams.page.toString(),
                        "search" to subVesselParams.search,
                        "company_member_id" to subVesselParams.companyMemberId,
                        "subsector_id" to subVesselParams.subSectorId,
                        "user_id" to subVesselParams.userId
                    ).apply {
                        if (!subVesselParams.hasSmartfarm) {
                            return@apply
                        }
                        put("has_smartfarm", "true")
                    }
                )
                    .lift(singleApiError())
                    .map { it.data ?: emptyList() }
            },
            mapData = {
                it.map {
                    it.toSubVesselEntity()
                }
            }
        )
    }

    override fun getListVessel(vesselparams: VesselParams): Flowable<List<VesselEntity>> {
        return networkSyncReverse<List<VesselItem>, List<VesselEntity>>(
            saveToDb = { dbService.monitoringDao().addAllVessel(it) },
            fetchDb = {
                var queryString = "SELECT * FROM vesselentity"

                /**
                 * Construct Query
                 */
                if (vesselparams.vesselName.isNotEmpty()) {
                    queryString += " WHERE name LIKE '%${vesselparams.vesselName}%'"
                }

                queryString += " LIMIT ${vesselparams.quantity} OFFSET" +
                    " (${vesselparams.quantity} * (${vesselparams.page} - 1))"

                queryString += ";"

                dbService.monitoringDao().getVessel(SimpleSQLiteQuery(queryString))
            },
            fetchApi = {
                webService.getListVessel(
                    vesselparams.page,
                    vesselparams.quantity,
                    vesselparams.vesselName,
                    vesselparams.userId,
                    vesselparams.status,
                    vesselparams.subSectorId
                )
                    .lift(singleApiError())
                    .map {
                        it.data!!
                    }
            },
            mapData = {
                it.map {
                    it.toVesselEntity()
                }
            }
        )
    }
}
