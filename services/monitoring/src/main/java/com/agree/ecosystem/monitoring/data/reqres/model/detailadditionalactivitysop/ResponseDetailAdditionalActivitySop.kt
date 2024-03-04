package com.agree.ecosystem.monitoring.data.reqres.model.detailadditionalactivitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.detailadditionalactivitysop.AdditionalSopActivityDetail

data class ResponseDetailAdditionalActivitySop(
    val code: Int,
    val data: List<DataDetailAdditionalActivitySop>,
    val message: String,
    val meta: Meta,
    val success: Boolean
) {
    fun toAdditionalSopActivityDetail(): List<AdditionalSopActivityDetail> {
        return data.map {
            AdditionalSopActivityDetail(
                formSchema = it.data,
                order = it.order,
                id = it.id
            )
        }
    }
}
