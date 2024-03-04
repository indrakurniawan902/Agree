package com.agree.ecosystem.partnership.data.reqres.model.validation

import com.agree.ecosystem.partnership.domain.reqres.model.validation.Validation
import com.google.gson.annotations.SerializedName

data class ValidationItem(
    @field:SerializedName("registered")
    val registered: String? = null,
    @field:SerializedName("subsectors")
    val subsectors: List<SubsectorValidateItem> = arrayListOf()
) {
    fun toValidation(): Validation {
        return Validation(
            registered = registered.orEmpty(),
            subsectors = subsectors?.map { it.toSubsectorValidate() }.orEmpty(),
        )
    }
}
