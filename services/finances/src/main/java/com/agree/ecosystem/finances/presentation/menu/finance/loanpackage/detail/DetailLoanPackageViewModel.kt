package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.detail

import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.finances.domain.reqres.FinanceUsecase
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class DetailLoanPackageViewModel(private val useCase: FinanceUsecase) : DevViewModel() {

    private val _data = DevData<DetailLoanPackage>().apply { default() }
    val data get() = _data

    fun fetchDetailLoanPackage(loanPackageId: String) {
        useCase.fetchDetailLoanPackage(loanPackageId)
            .setHandler(_data)
            .let {
                return@let disposable::add
            }
    }

}