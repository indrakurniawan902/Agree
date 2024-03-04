package com.agree.ecosystem.agreepedia.presentation.menu.home

import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.agreepedia.utility.enums.FilterSector

interface AgreepediaDataContract {

    fun fetchAgreepediaArticles()

    fun fetchLoadMoreAgreepediaArticles()

    fun onGetAgreepediaArticlesLoading()

    fun onGetAgreepediaArticlesSuccess(data: List<Article>)

    fun onGetAgreepediaArticlesEmpty()

    fun onGetAgreepediaArticlesFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onLoadMoreSuccess(data: List<Article>)

    fun onLoadMoreLoading()

    fun onLoadMoreFailed()

    fun onLoadMoreEmpty()

    fun onFilterStateChanged(sector: FilterSector)
}
