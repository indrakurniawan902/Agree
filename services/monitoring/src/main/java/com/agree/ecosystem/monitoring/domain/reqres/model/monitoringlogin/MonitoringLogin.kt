package com.agree.ecosystem.monitoring.domain.reqres.model.monitoringlogin

data class MonitoringLogin(
    val accessToken: String,
    val duration: Int,
    val tokenType: String
)
