package com.agree.ecosystem.monitoring.domain.reqres.model.activitysop

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.InsertActivitySopBundleParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.navigation.params.DetailActivityParams
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ActivitySopItemToDetailActivityParams(
    val activitySop: ActivitySop,
    val detailSubVessel: DetailSubVessel
) : Parcelable {
    fun getDetailActivityParams(): DetailActivityParams {
        return DetailActivityParams(
            idBundleParams = getInsertActivitySopBundleParams(),
            order = "",
            date = activitySop.date,
            activityName = activitySop.name,
            guideContent = activitySop.detail,
            isCompleted = activitySop.isCompleted,
            type = activitySop.type,
            recordType = detailSubVessel.recordType
        )
    }
    fun getInsertActivitySopBundleParams(): InsertActivitySopBundleParams {
        return InsertActivitySopBundleParams(
            commodityId = detailSubVessel.commodityId,
            commodityVarietyId = detailSubVessel.commodityVarietyId,
            companyMemberId = detailSubVessel.companyMemberId,
            companyId = detailSubVessel.companyId,
            vesselId = detailSubVessel.vesselId,
            workerId = detailSubVessel.workerId,
            subVesselId = detailSubVessel.id,
            phaseActivityId = activitySop.id
        )
    }
}
