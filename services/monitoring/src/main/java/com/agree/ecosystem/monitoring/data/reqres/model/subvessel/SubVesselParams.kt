package com.agree.ecosystem.monitoring.data.reqres.model.subvessel

import androidx.annotation.Keep

@Keep
data class SubVesselParams(
    val quantity: Int,
    val page: Int,
    val search: String,
    val companyMemberId: String,
    val subSectorId: String,
    var hasSmartfarm: Boolean
)
