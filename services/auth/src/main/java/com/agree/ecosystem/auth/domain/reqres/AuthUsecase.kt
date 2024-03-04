package com.agree.ecosystem.auth.domain.reqres

import com.agree.ecosystem.auth.domain.reqres.model.forgotAccount.ForgotAccount
import com.agree.ecosystem.auth.domain.reqres.model.forgotAccount.ForgotAccountPhoneNumber
import com.agree.ecosystem.auth.domain.reqres.model.login.Engine
import com.agree.ecosystem.auth.domain.reqres.model.login.Login
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.google.gson.JsonElement
import io.reactivex.Flowable

interface AuthUsecase {
    fun login(username: String, password: String): Flowable<Pair<Login, Engine>>

    fun logout(): Flowable<JsonElement>

    fun register(
        name: String,
        username: String,
        phoneNumber: String,
        email: String?,
        password: String,
        confirmPassword: String
    ): Flowable<Profile>

    fun forgotAccountEmail(
        email: String
    ): Flowable<ForgotAccount>

    fun forgotAccountPhoneNumber(
        phoneNumber: String
    ): Flowable<ForgotAccountPhoneNumber>

    fun getOtpPhoneNumber(
        phoneNumber: String
    ): Flowable<JsonElement>

    fun getOtpEmail(
        email: String
    ): Flowable<JsonElement>

    fun verifyOption(
        phoneNumber: String,
        otp: String
    ): Flowable<Login>

    fun verifyOptionEmail(
        email: String,
        otp: String
    ): Flowable<Login>
}
