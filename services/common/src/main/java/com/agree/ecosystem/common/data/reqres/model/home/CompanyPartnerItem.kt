package com.agree.ecosystem.common.data.reqres.model.home

import com.agree.ecosystem.common.domain.reqres.model.home.companypartner.CompanyPartner
import com.google.gson.annotations.SerializedName

data class CompanyPartnerItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("image")
    val image: String? = null,
) {
    fun toCompanyPartner(): CompanyPartner {
        return CompanyPartner(
            id = id.orEmpty(),
            userId = userId.orEmpty(),
            name = name.orEmpty(),
            image = image.orEmpty()
        )
    }
}
