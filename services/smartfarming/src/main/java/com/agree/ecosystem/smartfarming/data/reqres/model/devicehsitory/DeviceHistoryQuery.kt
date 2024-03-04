package com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory

import com.google.gson.annotations.SerializedName

data class DeviceHistoryQuery(
    @field:SerializedName("subvessel_id")
    val subVesselId: String,
    @field:SerializedName("smartfarm_partner_device_id")
    val smartFarmPartnerDeviceId: String,
)
