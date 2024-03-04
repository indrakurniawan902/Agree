package com.agree.ecosystem.agreepedia.domain

import com.agree.ecosystem.agreepedia.data.AgreepediaRepository
import com.agree.ecosystem.agreepedia.data.model.article.ArticleParams
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import io.reactivex.Flowable

class AgreepediaInteractor(
    private val repo: AgreepediaRepository
) : AgreepediaUsecase {

    override fun getAgreepediaArticles(params: ArticleParams): Flowable<List<Article>> {
        return repo.getAgreepediaArticles(params).map {
            it.map { item ->
                item.toAgreepedia()
            }
        }
    }
}
