package com.agree.ecosystem.utilities.data.reqres

import com.agree.ecosystem.utilities.data.reqres.model.zone.DistrictEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.ProvinceEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.SubDistrictEntity
import com.agree.ecosystem.utilities.data.reqres.model.zone.VillageEntity
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface ZoneRepository : DevRepository {

    fun getAllProvinces(): Flowable<List<ProvinceEntity>>

    fun getDistrictsByProvince(provinceId: String): Flowable<List<DistrictEntity>>

    fun getSubDistrictsByDistrict(districtId: String): Flowable<List<SubDistrictEntity>>

    fun getVillagesBySubDistrict(subDistrictId: String): Flowable<List<VillageEntity>>
}
