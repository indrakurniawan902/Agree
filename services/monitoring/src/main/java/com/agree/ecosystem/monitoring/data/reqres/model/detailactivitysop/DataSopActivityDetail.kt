package com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop

import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.google.gson.annotations.SerializedName

data class DataSopActivityDetail(
    val data: FormSchema,
    val id: String,
    val name: String,
    @SerializedName("is_insert")
    val isInsert: Boolean,
    val order: String,
    val repetition: Int
) {
    fun toSopActivityDetail(): SopActivityDetail {
        return SopActivityDetail(
            formSchema = this.data,
            id = this.id,
            name = this.name,
            isInsert = this.isInsert,
            order = this.order,
            repetition = this.repetition
        )
    }
}
