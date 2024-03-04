package com.agree.ecosystem.common.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.common.data.reqres.model.home.CompanyPartnerItem
import com.agree.ecosystem.common.data.reqres.web.AgreeCompaniesApi
import com.devbase.data.source.db.DbService
import com.devbase.data.utilities.rx.operators.flowableApiError
import io.reactivex.Flowable

@WorkerThread
class CompaniesDataStore(
    override val dbService: DbService? = null,
    override val webService: AgreeCompaniesApi
) : CompaniesRepository {

    override fun getCompanyPartner(): Flowable<List<CompanyPartnerItem>> {
        return webService.getCompanyPartner()
            .lift(flowableApiError())
            .map { it.data }
    }
}
