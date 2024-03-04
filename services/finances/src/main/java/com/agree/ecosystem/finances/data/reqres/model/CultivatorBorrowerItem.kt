package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.CultivatorBorrower
import com.google.gson.annotations.SerializedName

data class CultivatorBorrowerItem(

    @field:SerializedName("borrowerId")
    val borrowerId: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("name")
    val name: String? = null
) {
    fun toFarmerBorrower(): CultivatorBorrower {
        return CultivatorBorrower(
            borrowerId = borrowerId.orEmpty(),
            nik = nik.orEmpty(),
            image = image.orEmpty(),
            name = name.orEmpty()
        )
    }
}
