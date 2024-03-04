package com.agree.ecosystem.utilities.domain.reqres.model.help

import androidx.annotation.Keep
import com.google.gson.JsonElement

@Keep
data class Help(
    val data: JsonElement,
    val name: String,
    val type: String
)
