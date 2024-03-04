package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.listcultivator

import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.finances.domain.reqres.FinanceUsecase
import com.agree.ecosystem.finances.domain.reqres.model.CultivatorBorrower
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class ListCultivatorViewModel(private val useCase: FinanceUsecase) : DevViewModel() {
    var page = 1

    private val _mitraId = DevData<String>().apply { default() }
    val mitraId get() = _mitraId

    private val _lists = DevData<List<CultivatorBorrower>>().apply { default() }
    val lists get() = _lists

    private val _loadMoreLists = DevData<List<CultivatorBorrower>>().apply { default() }
    val loadMoreLists get() = _loadMoreLists

    fun fetchListCultivatorPartnership(mitraId: String) {
        page = 1
        useCase.fetchListCultivatorPartnership(mitraId)
            .setHandler(_lists)
            .let { return@let disposable::add }
    }

    fun fetchLoadMoreListCultivatorPartnership(userId: String) {
        page = 1
        useCase.fetchListCultivatorPartnership(userId)
            .setHandler(_loadMoreLists)
            .let { return@let disposable::add }
    }
}
