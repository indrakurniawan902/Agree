package com.agree.ecosystem.common.data.reqres.model.monitoring

import androidx.annotation.Keep

@Keep
data class SubVesselParams(
    val quantity: Int,
    val page: Int,
    val search: String,
    val companyMemberId: String,
    val subSectorId: String,
    val userId: String,
    var hasSmartfarm: Boolean
)
