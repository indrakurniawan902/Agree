package com.agree.ecosystem.partnership.data.reqres.model.validation

import com.agree.ecosystem.partnership.domain.reqres.model.validation.SubsectorValidate
import com.google.gson.annotations.SerializedName

data class SubsectorValidateItem(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("status")
    val status: String? = null,
) {
    fun toSubsectorValidate(): SubsectorValidate {
        return SubsectorValidate(
            id = id.orEmpty(),
            name = name.orEmpty(),
            status = status.orEmpty(),
        )
    }
}
