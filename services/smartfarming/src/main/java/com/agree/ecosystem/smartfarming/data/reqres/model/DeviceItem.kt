package com.agree.ecosystem.smartfarming.data.reqres.model

import com.agree.ecosystem.smartfarming.domain.reqres.model.tools.Device
import com.google.gson.annotations.SerializedName

class DeviceItem(

    @field:SerializedName("smartfarm_partner_service_name")
    val serviceName: String?,

    @field:SerializedName("smartfarm_partner_device_id")
    val deviceId: String?,

    @field:SerializedName("smartfarm_partner_device_name")
    val deviceName: String?,

    @field:SerializedName("smartfarm_partner_last_update")
    val lastUpdate: String?,

    @field:SerializedName("smartfarm_partner_device_image")
    val deviceImage: String?
) {
    fun toDeviceDataItem(): Device {
        return Device(
            serviceName = serviceName.orEmpty(),
            deviceId = deviceId.orEmpty(),
            deviceName = deviceName.orEmpty(),
            lastUpdate = lastUpdate.orEmpty(),
            deviceImage = deviceImage.orEmpty()
        )
    }
}