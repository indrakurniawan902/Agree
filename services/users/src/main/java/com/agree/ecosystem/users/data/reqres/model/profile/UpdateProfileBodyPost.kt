package com.agree.ecosystem.users.data.reqres.model.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateProfileBodyPost(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("address") val address: String,
    @SerializedName("province_id") val provinceId: String,
    @SerializedName("district_id") val districtId: String,
    @SerializedName("subdistrict_id") val subDistrictId: String,
    @SerializedName("village_id") val villageId: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("birth_date") val birthDate: String?,
    @SerializedName("job") val job: String
)
