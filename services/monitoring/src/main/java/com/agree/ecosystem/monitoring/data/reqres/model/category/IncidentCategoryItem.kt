package com.agree.ecosystem.monitoring.data.reqres.model.category

import com.agree.ecosystem.monitoring.domain.reqres.model.category.IncidentCategory
import com.google.gson.annotations.SerializedName

data class IncidentCategoryItem(
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("incident")
    val incident: List<String>?,
) {
    fun toIncidentCategory(): IncidentCategory {
        return IncidentCategory(
            name = name.orEmpty(),
            incident = incident?.map { it }.orEmpty()
        )
    }
}
