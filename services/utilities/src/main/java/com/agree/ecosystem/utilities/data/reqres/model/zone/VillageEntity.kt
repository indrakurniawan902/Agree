package com.agree.ecosystem.utilities.data.reqres.model.zone

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Village

@Entity
data class VillageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val villageId: String,
    val name: String,
    val subDistrictId: String
) {
    fun toVillage(): Village {
        return Village(
            id.orEmpty(),
            villageId.orEmpty(),
            name.orEmpty(),
            subDistrictId.orEmpty()
        )
    }
}
