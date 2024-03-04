package com.agree.ecosystem.agreepedia.presentation.menu.home

import com.agree.ecosystem.agreepedia.data.model.article.ArticleParams
import com.agree.ecosystem.agreepedia.domain.AgreepediaUsecase
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.agreepedia.utility.enums.FilterSector
import com.agree.ecosystem.agreepedia.utility.enums.FilterSort
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AgreepediaViewModel(
    private val usecase: AgreepediaUsecase
) : DevViewModel() {

    var page = 1

    private val _articles = DevData<List<Article>>().apply { default() }
    val articles = _articles.immutable()

    private val _loadMoreArticles = DevData<List<Article>>().apply { default() }
    val loadMoreArticles = _loadMoreArticles.immutable()

    val filterSort: MutableStateFlow<FilterSort> = MutableStateFlow(FilterSort.LATEST)

    val filterSector: MutableStateFlow<FilterSector> = MutableStateFlow(FilterSector.SHOWALL)

    fun getAgreepediaArticles(search: String) {
        page = 1
        usecase.getAgreepediaArticles(
            ArticleParams(
                page = page++,
                perPage = 10,
                blogName = search,
                orderDesc = filterSort.value.value,
                tag = if (filterSector.value != FilterSector.SHOWALL) {
                    filterSector.value.value
                } else {
                    null
                }
            )
        )
            .setHandler(_articles)
            .let { return@let disposable::add }
    }

    fun loadMoreAgreepediaArticles(search: String) {
        usecase.getAgreepediaArticles(
            ArticleParams(
                page = page++,
                perPage = 10,
                blogName = search,
                orderDesc = filterSort.value.value,
                tag = if (filterSector.value != FilterSector.SHOWALL) {
                    filterSector.value.value
                } else {
                    null
                }
            )
        )
            .setHandler(_loadMoreArticles)
            .let { return@let disposable::add }
    }
}
