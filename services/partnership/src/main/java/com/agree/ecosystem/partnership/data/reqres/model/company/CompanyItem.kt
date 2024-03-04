package com.agree.ecosystem.partnership.data.reqres.model.company

import com.agree.ecosystem.partnership.domain.reqres.model.company.*
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.google.gson.annotations.SerializedName

data class CompanyItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("scope_area")
    val scopeAreas: List<ScopeAreaItem>? = null,

    @field:SerializedName("subsectors")
    val subSector: List<SubSectorItem>? = null,

    @field:SerializedName("commodities")
    val commodity: List<CommodityItem>? = null,

    @field:SerializedName("partner_types")
    val partnerTypes: List<PartnerTypeItem>? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("about")
    val about: String? = null,

    @field:SerializedName("is_area")
    val isArea: Boolean? = null,

    @field:SerializedName("nickname")
    val nickname: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("created_by")
    val createdBy: String? = null,

    @field:SerializedName("modified_at")
    val modifiedAt: String? = null,

    @field:SerializedName("modified_by")
    val modifiedBy: String? = null
) {
    fun toCompany(): Company {
        return Company(
            id = id.orEmpty(),
            userId = userId.orEmpty(),
            image = image.orEmpty(),
            name = name.orEmpty(),
            scopeAreas = scopeAreas?.map { it.toScopeArea() }.orEmpty(),
            subSectors = subSector?.map { it.toSubSector() }.orEmpty(),
            commodities = commodity?.map { it.toCommodity() } ?: emptyList(),
            partnerTypes = partnerTypes?.map { it.toPartnerType() }.orEmpty(),
            address = address.orEmpty(),
            about = about.orEmpty(),
            isArea = isArea ?: false,
            nickname = nickname.orEmpty(),
            status = status.orEmpty(),
            createdAt = createdAt.orEmpty(),
            createdBy = createdBy.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            modifiedBy = modifiedBy.orEmpty()
        )
    }
}

data class CommodityItem(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("name")
    val name: String? = null
) {
    fun toCommodity(): Commodity {
        return Commodity(
            id.orEmpty(),
            name.orEmpty()
        )
    }
}

data class SubSectorItem(

    @field:SerializedName("sector_id")
    val sectorId: String? = null,

    @field:SerializedName("sector_name")
    val sectorName: String? = null,

    @field:SerializedName("subsector_id")
    val subSectorId: String? = null,

    @field:SerializedName("subsector_name")
    val subSectorName: String? = null
) {
    fun toSubSector(): SubSector {
        return SubSector(
            sectorId.orEmpty(),
            sectorName.orEmpty(),
            subSectorId.orEmpty(),
            subSectorName.orEmpty()
        )
    }
}

data class ScopeAreaItem(

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("province_name")
    val provinceName: String? = null
) {
    fun toScopeArea(): ScopeArea {
        return ScopeArea(
            provinceId.orEmpty(),
            provinceName.orEmpty()
        )
    }
}

data class PartnerTypeItem(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("subsector_id")
    val subSectorId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("desc")
    val desc: String? = null
) {
    fun toPartnerType(): PartnerType {
        return PartnerType(
            id.orEmpty(),
            subSectorId.orEmpty(),
            name.orEmpty(),
            desc.orEmpty()
        )
    }
}
