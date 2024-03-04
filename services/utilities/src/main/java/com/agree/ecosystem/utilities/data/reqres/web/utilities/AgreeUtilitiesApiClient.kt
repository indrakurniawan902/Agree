package com.agree.ecosystem.utilities.data.reqres.web.utilities

import com.agree.ecosystem.utilities.data.reqres.model.appinfo.AppInfoItem
import com.agree.ecosystem.utilities.data.reqres.model.help.HelpItem
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AgreeUtilitiesApiClient {
    @GET("utilities/v1/utilities")
    fun getAppInfo(
        @Query("utility_type") utilityType: String = "app-info"
    ): Single<Response<DevApiResponse<List<AppInfoItem>>>>

    @GET("utilities/v1/utilities")
    fun getHelpCategory(
        @Query("utility_type") utilityType: String = "help-center"
    ): Flowable<Response<DevApiResponse<ArrayList<HelpItem>>>>

    @GET("utilities/v1/subsectors")
    fun getSubSectors(): Single<Response<DevApiResponse<List<SubSectorItem>>>>
}
