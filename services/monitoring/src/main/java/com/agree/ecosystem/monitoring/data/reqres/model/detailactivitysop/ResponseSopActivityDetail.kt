package com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail

data class ResponseSopActivityDetail(
    val code: Int,
    val data: List<DataSopActivityDetail>,
    val message: String,
    val meta: Meta,
    val success: Boolean
) {
    fun toSopActivityDetailList(): List<SopActivityDetail> {
        return data.map {
            SopActivityDetail(
                formSchema = it.data,
                repetition = meta.quantity,
                id = it.id,
                name = it.name,
                isInsert = it.isInsert,
                order = it.order
            )
        }
    }
}
