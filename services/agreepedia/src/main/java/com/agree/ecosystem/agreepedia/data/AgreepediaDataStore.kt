package com.agree.ecosystem.agreepedia.data

import androidx.annotation.WorkerThread
import com.agree.ecosystem.agreepedia.data.model.article.ArticleItem
import com.agree.ecosystem.agreepedia.data.model.article.ArticleParams
import com.agree.ecosystem.agreepedia.data.web.AgreepediaApi
import com.agree.ecosystem.agreepedia.utility.enums.FilterSort
import com.devbase.data.source.db.DbService
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.devbase.utils.ext.isNotNull
import io.reactivex.Flowable

@WorkerThread
class AgreepediaDataStore(
    override val dbService: DbService? = null,
    override val webService: AgreepediaApi
) : AgreepediaRepository {

    override fun getAgreepediaArticles(params: ArticleParams): Flowable<List<ArticleItem>> {
        return webService.getAgreepediaArticles(
            mutableMapOf(
                "page" to params.page.toString(),
                "per_page" to params.perPage.toString(),
                "blog_name" to params.blogName,
                "order_desc" to if (params.orderDesc == FilterSort.LATEST.value) {
                    "change_date"
                } else {
                    ""
                },
                "order_asc" to if (params.orderDesc == FilterSort.OLDEST.value) {
                    "change_date"
                } else {
                    ""
                },
                "include[]" to "tag",
                "status" to "02",
                "action" to "1",
                "article_type" to "BLOG",
            ).apply {
                when {
                    params.oId.isNotNull() -> put("object_identifier", params.oId.toString())
                    params.tag.isNotNull() -> put("tag[]", params.tag.orEmpty())
                }
            }.toMap()
        ).lift(flowableApiError()).map { it.data }
    }
}
