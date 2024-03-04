package com.agree.ecosystem.agreepedia.data.web

import com.agree.ecosystem.agreepedia.data.model.article.ArticleItem
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import io.reactivex.Flowable
import retrofit2.Response

class AgreepediaApi(private val api: AgreepediaApiClient) : AgreepediaApiClient, WebService {
    override fun getAgreepediaArticles(params: Map<String, String?>): Flowable<Response<DevApiResponse<List<ArticleItem>>>> {
        return api.getAgreepediaArticles(params)
    }
    //    override fun getAgreepediaArticles(
//        page: Int,
//        perPage: Int,
//        oId: Long?,
//        blogName: String,
//        orderDesc: String?,
//        orderAsc: String?,
//        tag: String?,
//        include: String,
//        status: String,
//        action: Int
//    ): Flowable<Response<DevApiResponse<List<ArticleItem>>>> {
//        return api.getAgreepediaArticles(
//            page, perPage, oId, blogName, orderDesc, orderAsc, tag, include, status, action
//        )
//    }
}
