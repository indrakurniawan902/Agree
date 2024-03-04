package com.agree.ecosystem.utilities.data.reqres.model.zone

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.utilities.domain.reqres.model.zone.District

@Entity
data class DistrictEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val districtId: String,
    val name: String,
    val provinceId: String
) {
    fun toDistrict(): District {
        return District(
            id,
            districtId,
            name,
            provinceId
        )
    }
}
