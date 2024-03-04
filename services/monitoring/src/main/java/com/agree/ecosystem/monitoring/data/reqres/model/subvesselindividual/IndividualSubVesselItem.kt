package com.agree.ecosystem.monitoring.data.reqres.model.subvesselindividual

import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.google.gson.annotations.SerializedName

data class IndividualSubVesselItem(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("code")
    val code: String? = null
) {
    fun toIndividualSubVessel(): IndividualSubVessel {
        return IndividualSubVessel(
            id = id.orEmpty(),
            code = code.orEmpty()
        )
    }
}
