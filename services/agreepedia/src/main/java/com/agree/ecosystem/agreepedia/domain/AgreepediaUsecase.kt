package com.agree.ecosystem.agreepedia.domain

import com.agree.ecosystem.agreepedia.data.model.article.ArticleParams
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import io.reactivex.Flowable

interface AgreepediaUsecase {
    fun getAgreepediaArticles(params: ArticleParams): Flowable<List<Article>>
}
