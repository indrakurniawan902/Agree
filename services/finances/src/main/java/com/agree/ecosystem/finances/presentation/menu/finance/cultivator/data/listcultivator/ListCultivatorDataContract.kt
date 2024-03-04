package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.listcultivator

import com.agree.ecosystem.finances.domain.reqres.model.CultivatorBorrower

interface ListCultivatorDataContract {

    fun fetchListCultivator(mitraId: String)

    fun listCultivatorOnLoading()

    fun listCultivatorOnSuccess(data: List<CultivatorBorrower>)

    fun listCultivatorOnEmpty(data: List<CultivatorBorrower>)

    fun listCultivatorOnError(e: Throwable?)


    fun fetchLoadMoreListCultivator(mitraId: String)

    fun loadMoreListCultivatorOnLoading()

    fun loadMoreListCultivatorOnSuccess(data: List<CultivatorBorrower>)

    fun loadMoreListCultivatorOnEmpty(data: List<CultivatorBorrower>)

    fun loadMoreListCultivatorOnError(e: Throwable?)
}