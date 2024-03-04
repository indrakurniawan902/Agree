package com.agree.ecosystem.monitoring.data.reqres.model.monitoringlogin

import com.agree.ecosystem.monitoring.domain.reqres.model.monitoringlogin.MonitoringLogin
import com.google.gson.annotations.SerializedName

data class MonitoringLoginData(
    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("duration")
    val duration: Int,

    @field:SerializedName("token_type")
    val tokenType: String
) {
    fun toMonitoringLogin(): MonitoringLogin {
        return MonitoringLogin(
            duration = duration,
            accessToken = accessToken,
            tokenType = tokenType
        )
    }
}
