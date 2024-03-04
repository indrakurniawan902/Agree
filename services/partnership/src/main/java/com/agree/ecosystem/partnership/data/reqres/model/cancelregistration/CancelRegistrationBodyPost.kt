package com.agree.ecosystem.partnership.data.reqres.model.cancelregistration

import androidx.annotation.Keep

@Keep
data class CancelRegistrationBodyPost(
    val type: String = "status",
    val status: String = "cancelled"
)
