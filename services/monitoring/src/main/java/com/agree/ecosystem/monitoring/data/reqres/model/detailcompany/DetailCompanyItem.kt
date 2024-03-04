package com.agree.ecosystem.monitoring.data.reqres.model.detailcompany

import com.agree.ecosystem.monitoring.domain.reqres.model.detailcompany.DetailCompany
import com.google.gson.annotations.SerializedName

data class DetailCompanyItem(
    @field:SerializedName("commodity")
    val commodity: List<CommodityItem>? = null,
) {
    fun toDetailCompanyItem(): DetailCompany {
        return DetailCompany(
            commodity = commodity?.map { it.toCommodityProduct() }.orEmpty(),
        )
    }
}

data class CommodityItem(
    @field:SerializedName("commodityId")
    val commodityId: String?,
    @field:SerializedName("productType")
    val productType: List<String>?,
) {
    fun toCommodityProduct(): CommodityItem {
        return CommodityItem(
            commodityId = commodityId.orEmpty(),
            productType = productType?.map { it }.orEmpty()
        )
    }
}
