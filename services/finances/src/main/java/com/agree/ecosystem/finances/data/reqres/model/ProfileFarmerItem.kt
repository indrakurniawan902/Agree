package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.FarmerInfoPercentage
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.ProfileFarmer
import com.google.gson.annotations.SerializedName

data class ProfileFarmerItem(
    @field:SerializedName("borrowerId")
    val borrowerId: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("placeOfBirth")
    val placeOfBirth: String? = null,

    @field:SerializedName("education")
    val education: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("detailsInfo")
    val detailsInfo: List<Map<String, FarmerInfoPercentageItem>>? = null,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,

    @field:SerializedName("balanceBorrower")
    val balanceBorrower: Int? = null,

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("farmerId")
    val farmerId: List<String>? = listOf(),

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("contactNumber")
    val contactNumber: String? = null,

    @field:SerializedName("isActiveLoan")
    val isActiveLoan: Boolean? = false,

    @field:SerializedName("isActiveLoanId")
    val isActiveLoanId: String? = null,

    @field:SerializedName("expenditureBorrower")
    val expenditureBorrower: Int? = null
) {
    fun toProfileFarmer(): ProfileFarmer {
        return ProfileFarmer(
            borrowerId = borrowerId.orEmpty(),
            image = image.orEmpty(),
            placeOfBirth = placeOfBirth ?: "",
            education = education ?: "",
            gender = gender ?: "",
            detailsInfo = detailsInfo?.map { detailsInfo ->
                detailsInfo.mapValues { entryValues -> entryValues.value.toFarmerInfoPercentage() }
            } ?: emptyList(),
            dateOfBirth = dateOfBirth ?: "",
            balanceBorrower = balanceBorrower ?: 0,
            nik = nik ?: "-",
            farmerId = farmerId.orEmpty(),
            name = name ?: "-",
            contactNumber = contactNumber ?: "-",
            expenditureBorrower = expenditureBorrower ?: 0,
            isActiveLoan = isActiveLoan ?: false,
            isActiveLoanId = isActiveLoanId
        )
    }
}

data class FarmerInfoPercentageItem(
    @field:SerializedName("percentage")
    val percentage: Int? = null,

    @field:SerializedName("isCompleted")
    val isCompleted: Boolean? = null,

    @field:SerializedName("filledFields")
    val filledFields: Int? = null,

    @field:SerializedName("mandatoryFields")
    val mandatoryFields: Int? = null,
) {
    fun toFarmerInfoPercentage(): FarmerInfoPercentage {
        return FarmerInfoPercentage(
            percentage = percentage ?: 0,
            isCompleted = isCompleted ?: false,
            filledFields ?: 0,
            mandatoryFields ?: 0
        )
    }
}
