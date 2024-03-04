package com.agree.ecosystem.monitoring.domain.reqres.model.crudengineparams

data class CrudEngineParams(
    val key: String,
    val filter: String,
    val subVesselId: String = "",
    val date: String = ""
)
