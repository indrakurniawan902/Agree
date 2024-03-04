package com.agree.ecosystem.partnership.data.reqres.model.registrationstatus

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegistrationStatusQuery(
    @field:SerializedName("created_by") val createdBy: String
)
