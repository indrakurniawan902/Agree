package com.agree.ecosystem.common.data.reqres

import com.agree.ecosystem.common.data.reqres.model.home.MyLoanActiveItem
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface FinanceRepository : DevRepository {

    fun fetchListLoanActive(userId: String): Flowable<List<MyLoanActiveItem>>
}
