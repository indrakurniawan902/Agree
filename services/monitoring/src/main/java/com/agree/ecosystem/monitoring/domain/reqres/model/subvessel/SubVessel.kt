package com.agree.ecosystem.monitoring.domain.reqres.model.subvessel

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.R
import com.agree.ui.UiKitAttrs
import com.devbase.utils.util.getStringResource
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SubVessel(
    val id: String,
    val vesselId: String,
    val companyMemberId: String,
    val companySubMemberId: String,
    val companyName: String,
    val subSectorId: String,
    val commodityId: String,
    val commodityVarietyId: String,
    val commodityName: String,
    val districtId: String,
    val districtName: String,
    val subVesselName: String,
    val order: String,
    val status: Status,
    val datePlanted: String,
    val companyId: String,
    val size: String,
    val createdAt: String,
    val createdBy: String,
    val modifiedAt: String,
    val modifiedBy: String,
    val code: String,
    val sectorId: String,
    val workerName: String,
    val description: String,
    val sectorName: String,
    val target: Double,
    val vesselName: String,
    val varietyName: String,
    val name: String,
    val hasSmartfarm: Boolean,
) : Parcelable {
    enum class Status(val status: String, val textColor: Int) {
        ACTIVE(
            getStringResource(R.string.label_active),
            UiKitAttrs.success_normal
        ),
        INACTIVE(
            getStringResource(R.string.label_inactive),
            UiKitAttrs.colorTertiary_300
        )
    }
}
