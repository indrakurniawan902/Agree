package com.agree.ecosystem.utilities.data.reqres.model.zone

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.utilities.domain.reqres.model.zone.Province

@Entity
data class ProvinceEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val provinceId: String,
    val name: String
) {
    fun toProvince(): Province {
        return Province(
            id,
            provinceId,
            name
        )
    }
}
