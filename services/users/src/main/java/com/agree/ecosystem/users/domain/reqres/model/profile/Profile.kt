package com.agree.ecosystem.users.domain.reqres.model.profile

data class Profile(
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
)
