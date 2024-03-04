package com.agree.ecosystem.smartfarming.data.reqres.model

import com.google.gson.annotations.SerializedName

data class UpdateReadStatusRequestBody(
    @SerializedName("subvessel_id") val subVesselId: String,
    @SerializedName("smartfarm_partner_device_id") val smartFarmPartnerDeviceId: String,
    @SerializedName("date") val date: String,
    @SerializedName("key") val key: String
)
