package com.agree.ecosystem.finances.presentation.menu.finance.loanactive

import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.finances.domain.reqres.FinanceUsecase
import com.agree.ecosystem.finances.domain.reqres.model.MyLoanData
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class ListLoanActiveViewModel(private val useCase: FinanceUsecase) : DevViewModel() {
    var page = 1

    private val _userId = DevData<String>().apply { default() }
    val userId get() = _userId

    private val _lists = DevData<List<MyLoanData>>().apply { default() }
    val lists get() = _lists

    private val _loadMoreList = DevData<List<MyLoanData>>().apply { default() }
    val loadMoreLists get() = _loadMoreList

    fun fetchListLoanActive(userId: String) {
        page = 1
        useCase.fetchListActiveLoan(userId)
            .setHandler(_lists)
            .let {
                return@let disposable::add
            }
    }

    fun fetchLoadMoreListLoanActive(userId: String) {
        page = 1
        useCase.fetchListActiveLoan(userId)
            .setHandler(_loadMoreList)
            .let {
                return@let disposable::add
            }
    }

}