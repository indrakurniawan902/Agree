package com.agree.ecosystem.auth.data.reqres

import com.agree.ecosystem.auth.data.reqres.model.forgotAccount.ForgotAccountEmailItem
import com.agree.ecosystem.auth.data.reqres.model.forgotAccount.ForgotAccountPhoneNumberItem
import com.agree.ecosystem.auth.data.reqres.model.login.EngineItem
import com.agree.ecosystem.auth.data.reqres.model.login.LoginItem
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileItem
import com.devbase.data.source.DevRepository
import com.google.gson.JsonElement
import io.reactivex.Flowable

interface AuthRepository : DevRepository {
    fun login(username: String, password: String): Flowable<Pair<LoginItem, EngineItem>>
    fun logout(): Flowable<JsonElement>

    fun register(
        name: String,
        username: String,
        phoneNumber: String,
        email: String?,
        password: String,
        confirmPassword: String
    ): Flowable<ProfileItem>

    fun forgotAccountEmail(
        email: String
    ): Flowable<ForgotAccountEmailItem>

    fun forgotAccountPhoneNumber(
        phoneNumber: String
    ): Flowable<ForgotAccountPhoneNumberItem>

    fun getOtpPhoneNumber(
        phoneNumber: String
    ): Flowable<JsonElement>

    fun getOtpEmail(
        email: String
    ): Flowable<JsonElement>

    fun postVerifyOption(
        phoneNumber: String,
        otp: String
    ): Flowable<LoginItem>

    fun postVerifyOptionEmail(
        email: String,
        otp: String
    ): Flowable<LoginItem>
}
