package com.agree.ecosystem.utilities.data.reqres.model.help

import com.agree.ecosystem.utilities.domain.reqres.model.help.Help
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class HelpItem(
    @field:SerializedName("data")
    val data: JsonElement,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("type")
    val type: String? = null,
) {
    fun toHelp(): Help {
        return Help(
            data = data,
            name = name.orEmpty(),
            type = type.orEmpty()
        )
    }
}
