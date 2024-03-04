package com.agree.ecosystem.common.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.common.data.reqres.model.home.MyLoanActiveItem
import com.agree.ecosystem.common.data.reqres.model.home.MyLoanItem
import com.agree.ecosystem.common.data.reqres.web.AgreeFinanceApi
import com.devbase.data.source.db.DbService
import com.devbase.data.utilities.rx.operators.flowableApiError
import io.reactivex.Flowable

@WorkerThread
class FinanceDataStore(
    override val dbService: DbService? = null,
    override val webService: AgreeFinanceApi
) : FinanceRepository {

    private val listLoanActive = listOf(
        MyLoanActiveItem(
            true,
            listOf(
                MyLoanItem(
                    "edb49ebd-7a71-4db1-8b14-e1dc68ff3d59",
                    "GK1092228", 1000000, 0, 0,
                    listOf("petani 3"),
                    "Permodalan Bank Sumut",
                    "2022-09-29T04:23:41.538Z",
                    "2023-03-09T03:46:31.793Z"
                )
            )
        ),
        MyLoanActiveItem(
            true,
            listOf(
                MyLoanItem(
                    "7ae82917-858b-4914-be62-b7f9e7dd5a1a",
                    "AV111224", 2000000, 0, 0,
                    listOf("petani 4"),
                    "KUR MIKRO BNI",
                    "2022-11-15T09:31:48.832Z",
                    "2022-11-15T09:31:48.832Z"
                )
            )
        )
    )

    override fun fetchListLoanActive(userId: String): Flowable<List<MyLoanActiveItem>> {
        return webService.fetchListLoanActive(userId)
            .lift(flowableApiError())
            .map { it.data }
    }
}
