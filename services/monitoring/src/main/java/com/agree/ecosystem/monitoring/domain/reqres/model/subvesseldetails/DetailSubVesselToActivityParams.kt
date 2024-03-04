package com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.*

@Keep
@Parcelize
data class DetailSubVesselToActivityParams(
    var subVesselId: String?,
//    var detailSubVessel: DetailSubVessel?
) : Parcelable
