package com.agree.ecosystem.utilities.domain.reqres

import com.agree.ecosystem.utilities.domain.reqres.model.zone.District
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Province
import com.agree.ecosystem.utilities.domain.reqres.model.zone.SubDistrict
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Village
import io.reactivex.Flowable

class ZoneInteractor(
    private val repo: com.agree.ecosystem.utilities.data.reqres.ZoneRepository
) : ZoneUsecase {

    override fun getAllProvinces(): Flowable<List<Province>> {
        return repo.getAllProvinces().map { list ->
            list.map {
                it.toProvince()
            }
        }
    }

    override fun getDistrictsByProvince(provinceId: String): Flowable<List<District>> {
        return repo.getDistrictsByProvince(provinceId).map { list ->
            list.map {
                it.toDistrict()
            }
        }
    }

    override fun getSubDistrictsByDistrict(districtId: String): Flowable<List<SubDistrict>> {
        return repo.getSubDistrictsByDistrict(districtId).map { list ->
            list.map {
                it.toSubDistrict()
            }
        }
    }

    override fun getVillagesBySubDistrict(subDistrictId: String): Flowable<List<Village>> {
        return repo.getVillagesBySubDistrict(subDistrictId).map { list ->
            list.map {
                it.toVillage()
            }
        }
    }
}
