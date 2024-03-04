package com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop

data class AdditionalSopActivityDetailParams(
    val sortBy: String = "",
    val isDistinct: String = "",
    val columns: String = "id, name, data, activities.order",
    val filter: String,
    val pageSize: Int = 1,
    val pageNo: Int = 0
)
