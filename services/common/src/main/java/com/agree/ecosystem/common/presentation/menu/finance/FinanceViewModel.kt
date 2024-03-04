package com.agree.ecosystem.common.presentation.menu.finance

import com.agree.ecosystem.common.domain.reqres.FinanceUseCase
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoanActive
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class FinanceViewModel(
    private val useCase: FinanceUseCase
) : DevViewModel() {

    private val _lists = DevData<List<MyLoanActive>>().apply { default() }
    val lists get() = _lists

    fun fetchLoanActive(userId: String) {
        useCase.getListLoanActive(userId).setHandler(_lists)
            .let { return@let disposable::add }
    }
}
