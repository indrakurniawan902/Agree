package com.agree.ecosystem.users.data.reqres.model.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.core.utils.utility.offline.EntityModel
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile

@Entity
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val username: String,
    val name: String,
    val nik: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val provinceId: String,
    val provinceName: String,
    val districtId: String,
    val districtName: String,
    val subDistrictId: String,
    val subDistrictName: String,
    val villageId: String,
    val villageName: String,
    val avatar: String,
    val status: String,
    val createdAt: String,
    val modifiedAt: String,
    val gender: String,
    val birthDate: String,
    val job: String,
    val data: String
) : EntityModel {
    fun toProfile(): Profile {
        return Profile(
            id = id,
            username = username,
            name = name,
            nik = nik,
            phoneNumber = phoneNumber,
            email = email,
            address = address,
            provinceId = provinceId,
            provinceName = provinceName,
            districtId = districtId,
            districtName = districtName,
            subDistrictId = subDistrictId,
            subDistrictName = subDistrictName,
            villageId = villageId,
            villageName = villageName,
            avatar = avatar,
            status = status,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
            gender = gender,
            birthDate = birthDate,
            job = job,
            data = data
        )
    }
}
