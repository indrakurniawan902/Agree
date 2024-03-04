package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.*
import com.google.gson.annotations.SerializedName
import java.util.*

data class LoanDataItem(
    @field:SerializedName("loanPackageId")
    val loanPackageId: String? = null,

    @field:SerializedName("loanPackageName")
    val loanPackageName: String? = null,

    @field:SerializedName("loanPackageDescription")
    val loanPackageDescription: String? = null,

    @field:SerializedName("loanPackageImage")
    val loanPackageImage: String? = null,

    @field:SerializedName("loanPackageMaxAmount")
    val loanPackageMaxAmount: Int? = null,

    @field:SerializedName("loanPackageMinAmount")
    val loanPackageMinAmount: Int? = null,

    @field:SerializedName("loanPackageType")
    val loanPackageType: String? = null,

    @field:SerializedName("loanPackagePaymentType")
    val loanPackagePaymentType: String? = null,

    @field:SerializedName("lenderData")
    val lenderData: LenderDataItem? = null,

    @field:SerializedName("loanPackageInterestRate")
    val loanPackageInterestRate: Int? = null,

    @field:SerializedName("loanPackageTenor")
    val loanPackageTenor: Int? = null,

    @field:SerializedName("loanPackageTenorUnit")
    val loanPackageTenorUnit: String? = null,

    @field:SerializedName("loanPackageDataSchema")
    val loanPackageDataSchema: LoanPackageDataSchemaItem?,

    @field:SerializedName("loanPackageItemList")
    val loanPackageItemList: List<LoanPackageItemListItem>? = null

) {
    fun toListLoanModel(): LoanData {
        return LoanData(
            loanPackageId = loanPackageId ?: UUID.randomUUID().toString(),
            loanPackageName = loanPackageName ?: "Tidak ada nama paket pinjaman",
            loanPackageDescription = loanPackageDescription ?: "Tidak ada deskripsi paket pinjaman",
            image = loanPackageImage.orEmpty(),
            loanPackageMaxAmount = loanPackageMaxAmount ?: 0,
            loanPackageMinAmount = loanPackageMinAmount ?: 0,
            loanPackageType = loanPackageType.orEmpty(),
            lenderData = lenderData?.toLenderData() ?: LenderData(),
            loanPackageInterestRate = loanPackageInterestRate ?: 0,
            loanPackageTenor = loanPackageTenor ?: 0,
            loanPackageTenorUnit = loanPackageTenorUnit.orEmpty(),
            loanPackagePaymentType = loanPackagePaymentType.orEmpty(),
            loanPackageDataSchema = loanPackageDataSchema?.toLoanPackageDataSchema()
                ?: LoanPackageDataSchema(),
            loanPackageItemList = loanPackageItemList?.map { it.toLoanPackageItemList() }
                ?: emptyList()
        )
    }
}

data class LoanPackageDataSchemaItem(
    @field:SerializedName("properties") val properties: ProfilePropertiesItem?
) {
    fun toLoanPackageDataSchema() = LoanPackageDataSchema(
        properties = properties?.toProfileProperties() ?: ProfileProperties()
    )
}

data class ProfilePropertiesItem(
    @field:SerializedName("profileData")
    val personalInfo: ProfilePropertiesDetailItem?,

    @field:SerializedName("photosData")
    val photosData: ProfilePropertiesDetailItem?,

    @field:SerializedName("emergencyData")
    val emergencyData: ProfilePropertiesDetailItem?,

    @field:SerializedName("employmentData")
    val jobInfo: ProfilePropertiesDetailItem?,

    @field:SerializedName("addressData")
    val addressData: ProfilePropertiesDetailItem?,

    @field:SerializedName("familyData")
    val familyData: ProfilePropertiesDetailItem?,

    @field:SerializedName("spouseData")
    val spouseData: ProfilePropertiesDetailItem?,

    @field:SerializedName("businessData")
    val businessData: ProfilePropertiesDetailItem?,

    @field:SerializedName("assetData")
    val assetData: ProfilePropertiesDetailItem?,

    @field:SerializedName("collateralData")
    val collateralData: ProfilePropertiesDetailItem?,

    @field:SerializedName("bankData")
    val bankData: ProfilePropertiesDetailItem?,

    @field:SerializedName("legallityData")
    val legalityData: ProfilePropertiesDetailItem?
) {
    fun toProfileProperties() = ProfileProperties(
        personalInfo = personalInfo?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        photosData = photosData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        emergencyData = emergencyData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        jobInfo = jobInfo?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        addressData = addressData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        familyData = familyData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        spouseData = spouseData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        businessData = businessData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        assetData = assetData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        collateralData = collateralData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        bankData = bankData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail(),
        legalityData = legalityData?.toProfilePropertiesDetail() ?: ProfilePropertiesDetail()
    )
}

data class ProfilePropertiesDetailItem(
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("items")
    val items: ProfileRequiredFieldsItem?
) {
    fun toProfilePropertiesDetail() = ProfilePropertiesDetail(
        title = title.orEmpty(),
        items = items?.toProfileRequiredFields() ?: ProfileRequiredFields()
    )
}

data class ProfileRequiredFieldsItem(
    @field:SerializedName("required")
    val required: List<String>? = emptyList(),

    @field:SerializedName("properties")
    val properties: Map<String, ProfileItemsPropertiesItem>? = emptyMap()
) {
    fun toProfileRequiredFields() = ProfileRequiredFields(
        required = required ?: emptyList(),
        properties = properties?.mapValues { it.value.toProfileItemsProperties() } ?: emptyMap()
    )
}

data class ProfileItemsPropertiesItem(
    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("description")
    val description: String? = null
) {
    fun toProfileItemsProperties(): ProfileItemsProperties {
        return ProfileItemsProperties(
            title = title.orEmpty(),
            type = type.orEmpty(),
            description = description.orEmpty()
        )
    }
}

data class LoanPackageItemListItem(
    @field:SerializedName("itemName")
    val itemName: String? = null,

    @field:SerializedName("itemPrice")
    val itemPrice: Int? = null,

    @field:SerializedName("itemQuantity")
    val itemQuantity: Int? = null,

    @field:SerializedName("itemUnit")
    val itemUnit: String? = null,

    @field:SerializedName("itemMaxUnit")
    val itemMaxUnit: Int? = null
) {
    fun toLoanPackageItemList(): LoanPackageItemList {
        return LoanPackageItemList(
            itemName = itemName.orEmpty(),
            itemPrice = itemPrice ?: 0,
            itemQuantity = itemQuantity ?: 0,
            itemUnit = itemUnit.orEmpty(),
            itemMaxUnit = itemMaxUnit ?: 0
        )
    }
}

data class LenderDataItem(
    @field:SerializedName("lenderName")
    val lenderName: String? = null
) {
    fun toLenderData(): LenderData {
        return LenderData(
            lenderName = lenderName.orEmpty()
        )
    }
}
