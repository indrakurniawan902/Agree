package com.agree.ecosystem.agreepedia.presentation.navigation

import com.agree.ecosystem.agreepedia.domain.model.article.Article

interface MainNavigation {
    fun fromHomeToAgreepediaDetail(article: Article)

    fun fromDetailOpenLink(oId: Long)
}
