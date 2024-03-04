package com.agree.ecosystem.agreepedia.presentation.menu.detail

import com.agree.ecosystem.agreepedia.domain.model.article.Article

interface DetailDataContract {

    fun fetchAgreepediaDetail(oId: Long)

    fun onGetAgreepediaDetailLoading()

    fun onGetAgreepediaDetailEmpty()

    fun onGetAgreepediaDetailFailed(e: Throwable?) {
        // Do Nothing
    }

    fun onGetAgreepediaDetailSuccess(data: Article)
}
