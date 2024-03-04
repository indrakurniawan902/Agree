package com.agree.ecosystem.auth.domain.reqres.model.login

data class Login(
    val id: String,
    val accessToken: String,
    val username: String,
    val refreshToken: String,
    val phoneNumber: String
)
