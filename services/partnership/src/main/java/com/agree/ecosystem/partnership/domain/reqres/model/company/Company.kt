package com.agree.ecosystem.partnership.domain.reqres.model.company

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Company(
    val id: String,
    val userId: String,
    val image: String,
    val name: String,
    val scopeAreas: List<ScopeArea>,
    val subSectors: List<SubSector>,
    val commodities: List<Commodity>,
    val partnerTypes: List<PartnerType>,
    val address: String,
    val about: String,
    val isArea: Boolean,
    val nickname: String,
    val status: String,
    val createdAt: String,
    val createdBy: String,
    val modifiedAt: String,
    val modifiedBy: String
) : Parcelable

// @Keep
// @Parcelize
// data class Commodity(
//    val commodity_id: String,
//    val name: String
// ) : Parcelable

@Keep
@Parcelize
data class SubSector(
    val sectorId: String,
    val sectorName: String,
    val subSectorId: String,
    val subSectorName: String
) : Parcelable {
    fun getSubSectorLabel(): String {
        return sectorName.plus(" ").plus(subSectorName)
    }
}

@Keep
@Parcelize
data class ScopeArea(
    val provinceId: String,
    val provinceName: String
) : Parcelable

@Keep
@Parcelize
data class PartnerType(
    val id: String,
    val subSectorId: String,
    val name: String,
    val desc: String
) : Parcelable
