package com.agree.ecosystem.common.domain.reqres

import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoanActive
import io.reactivex.Flowable
import io.reactivex.Single

interface FinanceUseCase {
    fun getListLoanActive(userId: String): Flowable<List<MyLoanActive>>

    fun getModalActiveStatus(): Single<Boolean>
}
