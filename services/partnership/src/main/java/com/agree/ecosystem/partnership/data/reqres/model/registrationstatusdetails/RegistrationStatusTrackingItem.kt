package com.agree.ecosystem.partnership.data.reqres.model.registrationstatusdetails

import com.agree.ecosystem.monitoring.data.reqres.model.subvessel.SubVesselItem
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails.RegistrationStatusTracking
import com.google.gson.annotations.SerializedName

data class RegistrationStatusTrackingItem(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("submission_status")
    val submissionStatus: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("order")
    val order: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("data")
    val data: Data? = null
) {
    data class Data(
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("vessel_name")
        val vesselName: String? = null,
        @SerializedName("size")
        val size: Double? = null,
        @SerializedName("realization_size")
        val realizationSize: Double? = null,
        @SerializedName("district_name")
        val districtName: String? = null,
        @SerializedName("sub_vessel")
        val subVessel: List<SubVesselItem>? = null,
    )
    fun toRegistrationStatusTracking(): RegistrationStatusTracking {
        return RegistrationStatusTracking(
            description = description.orEmpty(),
            name = name.orEmpty(),
            status = when (status) {
                "pending" -> RegistrationStatusTracking.Status.PENDING
                "on_progress" -> RegistrationStatusTracking.Status.ON_PROGRESS
                "success" -> RegistrationStatusTracking.Status.SUCCESS
                "failed" -> RegistrationStatusTracking.Status.FAILED
                else -> RegistrationStatusTracking.Status.PENDING
            },
            submissionStatus = when (submissionStatus) {
                "pending" -> RegistrationStatus.Status.SUBMITTED
                "assigned" -> RegistrationStatus.Status.ON_PROGRESS
                "surveyed" -> RegistrationStatus.Status.ON_SURVEY
                "approved" -> RegistrationStatus.Status.ACCEPTED
                "rejected" -> RegistrationStatus.Status.REJECTED
                "cancelled" -> RegistrationStatus.Status.CANCELED
                else -> RegistrationStatus.Status.SUBMITTED
            },
            title = title.orEmpty(),
            order = order.orEmpty(),
            date = date.orEmpty(),
            rejectionReason = data?.description.orEmpty(),
            vesselName = data?.vesselName.orEmpty(),
            size = data?.size ?: 0.0,
            realizationSize = data?.realizationSize ?: 0.0,
            districtName = data?.districtName.orEmpty(),
            subVessel = data?.subVessel?.map { it.toSubVessel() }.orEmpty()
        )
    }
}
