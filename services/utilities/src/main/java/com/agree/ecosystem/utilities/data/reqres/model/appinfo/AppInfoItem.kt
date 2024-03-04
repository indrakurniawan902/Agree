package com.agree.ecosystem.utilities.data.reqres.model.appinfo

import com.google.gson.annotations.SerializedName

data class AppInfoItem(
    @field:SerializedName("about")
    val about: String? = null,

    @field:SerializedName("contact")
    val contact: String? = null,

    @field:SerializedName("help_center")
    val helpCenter: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("term")
    val term: String? = null,

    @field:SerializedName("utility_type")
    val utilityType: String? = null,
) {
    fun toAppInfoEntity(): AppInfoEntity {
        return AppInfoEntity(
            id = id ?: 0,
            about = about.orEmpty(),
            contact = contact.orEmpty(),
            helpCenter = helpCenter.orEmpty(),
            term = term.orEmpty(),
            utilityType = utilityType.orEmpty(),
        )
    }
}
