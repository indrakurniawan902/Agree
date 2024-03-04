package com.agree.ecosystem.agreepedia.data

import com.agree.ecosystem.agreepedia.data.model.article.ArticleItem
import com.agree.ecosystem.agreepedia.data.model.article.ArticleParams
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface AgreepediaRepository : DevRepository {
    fun getAgreepediaArticles(params: ArticleParams): Flowable<List<ArticleItem>>
}
