package com.agree.ecosystem.utilities.data.reqres.web.utilities

import com.agree.ecosystem.utilities.data.reqres.model.appinfo.AppInfoItem
import com.agree.ecosystem.utilities.data.reqres.model.help.HelpItem
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

class AgreeUtilitiesApi(
    private val api: AgreeUtilitiesApiClient
) : AgreeUtilitiesApiClient, WebService {

    override fun getAppInfo(utilityType: String): Single<Response<DevApiResponse<List<AppInfoItem>>>> {
        return api.getAppInfo()
    }

    override fun getHelpCategory(utilityType: String): Flowable<Response<DevApiResponse<ArrayList<HelpItem>>>> {
        return api.getHelpCategory()
    }

    override fun getSubSectors(): Single<Response<DevApiResponse<List<SubSectorItem>>>> {
        return api.getSubSectors()
    }
}
