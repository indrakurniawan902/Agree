package com.agree.ecosystem.partnership.domain.reqres.model.registrationstatusdetails

import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ui.UiKitAttrs

data class RegistrationStatusTracking(
    val description: String,
    val name: String,
    val status: Status,
    val submissionStatus: RegistrationStatus.Status,
    val title: String,
    val order: String,
    val date: String,
    val rejectionReason: String,
    var subSector: List<SubSector> = emptyList(),
    val vesselName: String,
    val size: Double,
    val realizationSize: Double,
    val districtName: String,
    val subVessel: List<SubVessel>
) {
    enum class Status(val textColor: Int, val lineColor: Int) {
        PENDING(UiKitAttrs.black_200, UiKitAttrs.black_200),
        ON_PROGRESS(UiKitAttrs.colorPrimary, UiKitAttrs.black_200),
        SUCCESS(UiKitAttrs.colorPrimary, UiKitAttrs.colorPrimary200),
        FAILED(UiKitAttrs.error_normal, UiKitAttrs.error_100)
    }
}
