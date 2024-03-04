package com.agree.ecosystem.utilities.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.utilities.data.reqres.db.ZoneDb
import com.agree.ecosystem.utilities.data.reqres.model.zone.DistrictEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.ProvinceEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.SubDistrictEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.VillageEntity
import com.agree.ecosystem.utilities.data.reqres.web.zone.AgreeZoneApi
import com.devbase.data.utilities.rx.operators.singleApiError
import com.oratakashi.viewbinding.core.network.rxjava2.flowable.networkSync
import io.reactivex.Flowable

@WorkerThread
class ZoneDataStore(
    override val dbService: ZoneDb,
    override val webService: AgreeZoneApi
) : ZoneRepository {

    override fun getAllProvinces(): Flowable<List<ProvinceEntity>> {
        return networkSync(
            saveToDb = { dbService.province().addAll(it) },
            fetchDb = { dbService.province().getAll() },
            fetchApi = {
                webService.getAllProvinces().lift(singleApiError()).map { it.data ?: emptyList() }
            },
            onConflict = { dataFromAPI, _ ->
                dataFromAPI.map { data -> data.toProviceEntity() }
            },
            alwaysUpToDate = { it.isEmpty() }
        )
    }

    override fun getDistrictsByProvince(provinceId: String): Flowable<List<DistrictEntity>> {
        return networkSync(
            saveToDb = { dbService.district().addAll(it) },
            fetchDb = { dbService.district().getByIdProvince(provinceId) },
            fetchApi = {
                webService.getDistrictsByProvince(provinceId).lift(singleApiError())
                    .map { it.data ?: emptyList() }
            },
            onConflict = { dataFromAPI, _ ->
                dataFromAPI.map { it.toDistrictEntity() }
            },
            alwaysUpToDate = { it.isEmpty() }
        )
    }

    override fun getSubDistrictsByDistrict(districtId: String): Flowable<List<SubDistrictEntity>> {
        return networkSync(
            saveToDb = { dbService.subDistrict().addAll(it) },
            fetchDb = { dbService.subDistrict().getByIdDistrict(districtId) },
            fetchApi = {
                webService.getSubDistrictsByDistrict(districtId)
                    .lift(singleApiError())
                    .map { it.data ?: emptyList() }
            },
            onConflict = { dataFromAPI, _ ->
                dataFromAPI.map { it.toSubDistrictEntity() }
            },
            alwaysUpToDate = { it.isEmpty() }
        )
    }

    override fun getVillagesBySubDistrict(subDistrictId: String): Flowable<List<VillageEntity>> {
        return networkSync(
            saveToDb = { dbService.village().addAll(it) },
            fetchDb = { dbService.village().getAllBySubDistrictId(subDistrictId) },
            fetchApi = {
                webService.getVillagesBySubDistrict(subDistrictId)
                    .lift(singleApiError())
                    .map { it.data ?: emptyList() }
            },
            onConflict = { dataFromAPI, _ ->
                dataFromAPI.map { it.toVillageEntity() }
            },
            alwaysUpToDate = { it.isEmpty() }
        )
    }
}
