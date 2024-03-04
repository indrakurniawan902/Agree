package com.agree.locales.data.reqres.web

import com.agree.locales.data.reqres.model.locale.LocaleResponseData
import com.devbase.data.source.web.WebService
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface LocaleApiClient : WebService {

    @GET("v1/db/data/noco/p_fsigb6yzdkj6dp/ecosystem-dev/views/Dictionary?limit=25000&offset=0")
    fun getResourcesDev(
        @Header("xc-token") token: String
    ): Single<Response<LocaleResponseData?>>

    @GET("v1/db/data/noco/p_fsigb6yzdkj6dp/ecosystem-prod/views/Dictionary?limit=25000&offset=0")
    fun getResourcesProd(
        @Header("xc-token") token: String
    ): Single<Response<LocaleResponseData?>>
}