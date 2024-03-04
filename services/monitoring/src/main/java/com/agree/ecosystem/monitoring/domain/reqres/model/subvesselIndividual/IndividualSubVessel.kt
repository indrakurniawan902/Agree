package com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class IndividualSubVessel(
    val id: String,
    var code: String
) : Parcelable
