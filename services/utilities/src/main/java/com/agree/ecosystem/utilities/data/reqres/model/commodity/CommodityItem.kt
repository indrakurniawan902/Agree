package com.agree.ecosystem.utilities.data.reqres.model.commodity

import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.google.gson.annotations.SerializedName

data class CommodityItem(
    @field:SerializedName("id")
    val id: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("target")
    val target: String? = null,
    @field:SerializedName("bruto")
    val bruto: String? = null,
    @field:SerializedName("netto")
    val netto: String? = null,
    @field:SerializedName("image")
    val image: String? = null,
    @field:SerializedName("subsector_id")
    val subSectorId: String? = null
) {
    fun toCommodity(): Commodity {
        return Commodity(
            id = id.orEmpty(),
            name = name.orEmpty(),
            target = target.orEmpty(),
            bruto = bruto.orEmpty(),
            netto = netto.orEmpty(),
            image = image.orEmpty(),
            subSectorId = subSectorId.orEmpty()
        )
    }
}
