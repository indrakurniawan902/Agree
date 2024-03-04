package com.agree.ecosystem.utilities.domain.reqres

import com.agree.ecosystem.utilities.domain.reqres.model.zone.District
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Province
import com.agree.ecosystem.utilities.domain.reqres.model.zone.SubDistrict
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Village
import io.reactivex.Flowable

interface ZoneUsecase {
    fun getAllProvinces(): Flowable<List<Province>>

    fun getDistrictsByProvince(provinceId: String): Flowable<List<District>>

    fun getSubDistrictsByDistrict(districtId: String): Flowable<List<SubDistrict>>

    fun getVillagesBySubDistrict(subDistrictId: String): Flowable<List<Village>>
}
