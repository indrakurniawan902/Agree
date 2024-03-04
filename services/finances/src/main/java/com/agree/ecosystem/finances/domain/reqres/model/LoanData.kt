package com.agree.ecosystem.finances.domain.reqres.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Keep
@Parcelize
data class LoanData(
    val loanPackageId: String,
    val loanPackageName: String,
    val loanPackageDescription: String,
    val image: String,
    val loanPackageMaxAmount: Int,
    val loanPackageMinAmount: Int,
    var loanPackageType: String,
    val lenderData: LenderData,
    val loanPackageInterestRate: Int,
    val loanPackageTenor: Int,
    val loanPackageTenorUnit: String,
    val loanPackagePaymentType: String,
    val loanPackageDataSchema: LoanPackageDataSchema,
    val loanPackageItemList: List<@RawValue LoanPackageItemList>
) : Parcelable

@Keep
@Parcelize
data class LoanPackageDataSchema(
    val properties: ProfileProperties = ProfileProperties()
) : Parcelable

@Keep
@Parcelize
data class ProfileProperties(
    val personalInfo: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val photosData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val emergencyData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val jobInfo: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val addressData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val familyData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val spouseData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val businessData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val assetData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val collateralData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val bankData: ProfilePropertiesDetail = ProfilePropertiesDetail(),
    val legalityData: ProfilePropertiesDetail = ProfilePropertiesDetail()
) : Parcelable

@Keep
@Parcelize
data class ProfilePropertiesDetail(
    val title: String = "",
    val items: ProfileRequiredFields = ProfileRequiredFields()
) : Parcelable

@Keep
@Parcelize
data class ProfileRequiredFields(
    val required: List<String> = emptyList(),
    val properties: Map<String, @RawValue ProfileItemsProperties> = emptyMap()
) : Parcelable

@Keep
@Parcelize
data class ProfileItemsProperties(
    val title: String = "",
    val type: String = "",
    val description: String = ""
) : Parcelable

@Keep
@Parcelize
data class LoanPackageItemList(
    val itemName: String,
    val itemPrice: Int,
    val itemQuantity: Int,
    val itemUnit: String,
    val itemMaxUnit: Int
) : Parcelable

@Keep
@Parcelize
data class LenderData(
    val lenderName: String = ""
) : Parcelable
