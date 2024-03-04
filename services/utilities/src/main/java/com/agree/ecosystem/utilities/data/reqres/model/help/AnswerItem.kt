package com.agree.ecosystem.utilities.data.reqres.model.help

import com.agree.ecosystem.utilities.domain.reqres.model.help.Answer
import com.google.gson.annotations.SerializedName

data class AnswerItem(
    @field:SerializedName("desc")
    val desc: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("type")
    val type: String? = null
) {
    fun toAnswer(): Answer {
        return Answer(
            desc = desc.orEmpty(),
            image = image.orEmpty(),
            type = type.orEmpty()
        )
    }
}
