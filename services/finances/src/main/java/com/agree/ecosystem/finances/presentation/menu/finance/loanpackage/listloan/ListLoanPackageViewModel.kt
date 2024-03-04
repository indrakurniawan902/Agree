package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.listloan

import androidx.lifecycle.viewModelScope
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.finances.domain.reqres.FinanceUsecase
import com.agree.ecosystem.finances.domain.reqres.model.LoanPackage
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import kotlinx.coroutines.launch

class ListLoanPackageViewModel(
    private val useCase: FinanceUsecase
) : DevViewModel() {
    var page = 1

    private val _userId = DevData<String>().apply { default() }
    val userId get() = _userId

    private val _lists = DevData<List<LoanPackage>>().apply { default() }
    val lists get() = _lists.immutable()

    private val _loadMoreLists = DevData<List<LoanPackage>>().apply { default() }
    val loadMoreLists get() = _loadMoreLists.immutable()

    fun fetchlistLoanPackage() {
        page = 1
        useCase.fetchListLoanPackage("superapps")
            .setHandler(_lists)
            .let { return@let disposable::add }
    }

    fun fetchLoadMoreLoanPackage() {
        useCase.fetchListLoanPackage("superapps")
            .setHandler(_loadMoreLists)
            .let { return@let disposable::add }
    }


}
