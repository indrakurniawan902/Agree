package com.agree.ecosystem.agreepedia.data.web

import com.agree.ecosystem.agreepedia.data.model.article.ArticleItem
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AgreepediaApiClient {
    @GET("api/blog/list")
    fun getAgreepediaArticles(
        @QueryMap params: Map<String, String?>
    ): Flowable<Response<DevApiResponse<List<ArticleItem>>>>
}
