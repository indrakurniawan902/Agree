package com.agree.ecosystem.utilities.data.reqres.web.zone

import com.agree.ecosystem.utilities.data.reqres.model.zone.DistrictItem
import com.agree.ecosystem.utilities.data.reqres.model.zone.ProvinceItem
import com.agree.ecosystem.utilities.data.reqres.model.zone.SubDistrictItem
import com.agree.ecosystem.utilities.data.reqres.model.zone.VillageItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Single
import retrofit2.Response

class AgreeZoneApi(
    private val api: AgreeZoneApiClient
) : AgreeZoneApiClient, WebService {

    override fun getAllProvinces(): Single<Response<DevApiResponse<List<ProvinceItem>>>> {
        return api.getAllProvinces()
    }

    override fun getDistrictsByProvince(provinceId: String): Single<Response<DevApiResponse<List<DistrictItem>>>> {
        return api.getDistrictsByProvince(provinceId)
    }

    override fun getSubDistrictsByDistrict(districtId: String): Single<Response<DevApiResponse<List<SubDistrictItem>>>> {
        return api.getSubDistrictsByDistrict(districtId)
    }

    override fun getVillagesBySubDistrict(subDistrictId: String): Single<Response<DevApiResponse<List<VillageItem>>>> {
        return api.getVillagesBySubDistrict(subDistrictId)
    }
}
