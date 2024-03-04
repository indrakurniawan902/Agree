package com.agree.ecosystem.common.domain.reqres

import com.agree.ecosystem.common.data.reqres.FinanceRepository
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoanActive
import com.agree.ecosystem.core.utils.data.reqres.RemoteConfigRepository
import io.reactivex.Flowable
import io.reactivex.Single

class FinanceInteractor(
    private val repo: FinanceRepository,
    private val remoteConfig: RemoteConfigRepository
) : FinanceUseCase {
    override fun getListLoanActive(userId: String): Flowable<List<MyLoanActive>> {
        return repo.fetchListLoanActive(userId).map {
            it.map { it.toMyLoanData() }
        }
    }

    override fun getModalActiveStatus(): Single<Boolean> {
        return Single.just(remoteConfig.getBoolean("feature_agree_modal"))
    }
}
