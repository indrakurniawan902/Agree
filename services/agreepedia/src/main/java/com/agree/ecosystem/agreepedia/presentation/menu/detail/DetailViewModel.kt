package com.agree.ecosystem.agreepedia.presentation.menu.detail

import com.agree.ecosystem.agreepedia.data.model.article.ArticleParams
import com.agree.ecosystem.agreepedia.domain.AgreepediaUsecase
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DetailViewModel(
    private val usecase: AgreepediaUsecase
) : DevViewModel() {
    private val _articleDetail = DevData<List<Article>>().apply { default() }
    val articleDetail = _articleDetail.immutable()

    fun getAgreepediaDetail(oId: Long) {
        usecase.getAgreepediaArticles(
            ArticleParams(
                page = 1,
                perPage = 1,
                blogName = "",
                oId = oId
            )
        )
            .setHandler(_articleDetail)
            .let { return@let disposable::add }
    }
}
