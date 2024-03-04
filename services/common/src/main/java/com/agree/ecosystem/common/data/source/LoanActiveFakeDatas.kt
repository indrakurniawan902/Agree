package com.agree.ecosystem.common.data.source

import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoan
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoanActive

class LoanActiveFakeDatas {
    val listLoanActive = listOf(
        MyLoanActive(
            true,
            listOf(
                MyLoan(
                    "edb49ebd-7a71-4db1-8b14-e1dc68ff3d59",
                    "GK1092228", 1000000, 0, 0,
                    listOf("petani 3, petani 1, petani 5, lalala, yeyeye"),
                    "Permodalan Bank Sumut",
                    "2022-09-29T04:23:41.538Z",
                    "2023-03-09T03:46:31.793Z"
                ),
                MyLoan(
                    "7ae82917-858b-4914-be62-b7f9e7dd5a1a",
                    "AV111224", 2000000, 0, 0,
                    listOf("petani 4"),
                    "KUR MIKRO BNI",
                    "2022-11-15T09:31:48.832Z",
                    "2022-11-15T09:31:48.832Z"
                ),
                MyLoan(
                    "",
                    "", 0, 0, 0,
                    listOf(),
                    "",
                    "",
                    ""
                )
            )
        )
    )
}
