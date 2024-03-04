package com.agree.ecosystem.utilities.data.reqres.model.appinfo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.utilities.domain.reqres.model.appinfo.AppInfo

@Entity
data class AppInfoEntity(
    val about: String,
    val contact: String,
    val helpCenter: String,
    @PrimaryKey(autoGenerate = false) val id: Int,
    val term: String,
    val utilityType: String
) {
    fun toAppInfo(): AppInfo {
        return AppInfo(
            id = id,
            about = about,
            contact = contact,
            helpCenter = helpCenter,
            term = term,
            utilityType = utilityType,
        )
    }
}
