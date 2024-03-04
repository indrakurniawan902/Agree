package com.agree.ecosystem.utilities.data.reqres.web.zone

import com.agree.ecosystem.utilities.data.reqres.model.zone.DistrictItem
import com.agree.ecosystem.utilities.data.reqres.model.zone.ProvinceItem
import com.agree.ecosystem.utilities.data.reqres.model.zone.SubDistrictItem
import com.agree.ecosystem.utilities.data.reqres.model.zone.VillageItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AgreeZoneApiClient {
    @GET("utilities/v1/provinces")
    fun getAllProvinces(): Single<Response<DevApiResponse<List<ProvinceItem>>>>

    @GET("utilities/v1/districts")
    fun getDistrictsByProvince(
        @Query("province_id") provinceId: String
    ): Single<Response<DevApiResponse<List<DistrictItem>>>>

    @GET("utilities/v1/subdistricts")
    fun getSubDistrictsByDistrict(
        @Query("district_id") districtId: String
    ): Single<Response<DevApiResponse<List<SubDistrictItem>>>>

    @GET("utilities/v1/villages")
    fun getVillagesBySubDistrict(
        @Query("subdistrict_id") subDistrictId: String
    ): Single<Response<DevApiResponse<List<VillageItem>>>>
}
