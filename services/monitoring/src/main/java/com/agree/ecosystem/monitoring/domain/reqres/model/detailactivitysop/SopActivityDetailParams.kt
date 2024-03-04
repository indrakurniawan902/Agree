package com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop

data class SopActivityDetailParams(
    val columns: String = "id,data",
    val filter: String,
    val pageSize: Int = 10,
    val pageNo: Int = 0
)
