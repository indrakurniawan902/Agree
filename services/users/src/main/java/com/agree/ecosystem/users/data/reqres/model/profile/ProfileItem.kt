package com.agree.ecosystem.users.data.reqres.model.profile

import com.agree.ecosystem.core.utils.utility.offline.WebModel
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.google.gson.annotations.SerializedName

data class ProfileItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("province_name")
    val provinceName: String? = null,

    @field:SerializedName("district_id")
    val districtId: String? = null,

    @field:SerializedName("district_name")
    val districtName: String? = null,

    @field:SerializedName("subdistrict_id")
    val subDistrictId: String? = null,

    @field:SerializedName("subdistrict_name")
    val subDistrictName: String? = null,

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("village_name")
    val villageName: String? = null,

    @field:SerializedName("avatar")
    val avatar: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("created_by")
    val createdBy: String? = null,

    @field:SerializedName("modified_at")
    val modifiedAt: String? = null,

    @field:SerializedName("modified_by")
    val modifiedBy: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("birth_date")
    val birthDate: String? = null,

    @field:SerializedName("job")
    val job: String? = null,

    @field:SerializedName("data")
    val data: String? = null
) : WebModel {
    fun toProfileEntity(): ProfileEntity {
        return ProfileEntity(
            id = id.orEmpty(),
            username = username.orEmpty(),
            name = name.orEmpty(),
            nik = nik.orEmpty(),
            phoneNumber = phoneNumber.orEmpty(),
            email = email.orEmpty(),
            address = address.orEmpty(),
            provinceId = provinceId.orEmpty(),
            provinceName = provinceName.orEmpty(),
            districtId = districtId.orEmpty(),
            districtName = districtName.orEmpty(),
            subDistrictId = subDistrictId.orEmpty(),
            subDistrictName = subDistrictName.orEmpty(),
            villageId = villageId.orEmpty(),
            villageName = villageName.orEmpty(),
            avatar = avatar.orEmpty(),
            status = status.orEmpty(),
            createdAt = createdAt.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            gender = gender.orEmpty(),
            birthDate = birthDate.orEmpty(),
            job = job.orEmpty(),
            data = data.orEmpty()
        )
    }

    fun toProfile(): Profile {
        return Profile(
            id = id.orEmpty(),
            username = username.orEmpty(),
            name = name.orEmpty(),
            nik = nik.orEmpty(),
            phoneNumber = phoneNumber.orEmpty(),
            email = email.orEmpty(),
            address = address.orEmpty(),
            provinceId = provinceId.orEmpty(),
            provinceName = provinceName.orEmpty(),
            districtId = districtId.orEmpty(),
            districtName = districtName.orEmpty(),
            subDistrictId = subDistrictId.orEmpty(),
            subDistrictName = subDistrictName.orEmpty(),
            villageId = villageId.orEmpty(),
            villageName = villageName.orEmpty(),
            avatar = avatar.orEmpty(),
            status = status.orEmpty(),
            createdAt = createdAt.orEmpty(),
            modifiedAt = modifiedAt.orEmpty(),
            gender = gender.orEmpty(),
            birthDate = birthDate.orEmpty(),
            job = job.orEmpty(),
            data = data.orEmpty()
        )
    }
}
