package com.agree.ecosystem.utilities.data.reqres.model.zone

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.utilities.domain.reqres.model.zone.SubDistrict

@Entity
data class SubDistrictEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val subDistrictId: String,
    val name: String,
    val districtId: String
) {
    fun toSubDistrict(): SubDistrict {
        return SubDistrict(
            id,
            subDistrictId,
            name,
            districtId
        )
    }
}
