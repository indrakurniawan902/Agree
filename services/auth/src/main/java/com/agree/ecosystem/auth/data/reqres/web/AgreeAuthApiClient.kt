package com.agree.ecosystem.auth.data.reqres.web

import com.agree.ecosystem.auth.data.reqres.model.forgotAccount.ForgotAccountEmailBodyPost
import com.agree.ecosystem.auth.data.reqres.model.forgotAccount.ForgotAccountEmailItem
import com.agree.ecosystem.auth.data.reqres.model.forgotAccount.ForgotAccountPhoneNumberBodyPost
import com.agree.ecosystem.auth.data.reqres.model.forgotAccount.ForgotAccountPhoneNumberItem
import com.agree.ecosystem.auth.data.reqres.model.login.EngineBodyPost
import com.agree.ecosystem.auth.data.reqres.model.login.EngineItem
import com.agree.ecosystem.auth.data.reqres.model.login.LoginBodyPost
import com.agree.ecosystem.auth.data.reqres.model.login.LoginItem
import com.agree.ecosystem.auth.data.reqres.model.logout.LogoutBodyPost
import com.agree.ecosystem.auth.data.reqres.model.register.RegisterBodyPost
import com.agree.ecosystem.auth.data.reqres.model.verification.OtpBodyPostEmail
import com.agree.ecosystem.auth.data.reqres.model.verification.OtpBodyPostPhoneNumber
import com.agree.ecosystem.auth.data.reqres.model.verification.VerificationBodyPost
import com.agree.ecosystem.auth.data.reqres.model.verification.VerificationBodyPostEmail
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileItem
import com.devbase.data.source.web.model.DevApiResponse
import com.google.gson.JsonElement
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AgreeAuthApiClient {
    @POST("users/v1/users/login")
    fun postLogin(
        @Body data: LoginBodyPost
    ): Flowable<Response<DevApiResponse<LoginItem>>>

    @POST("engine/v1/login")
    fun postLoginCrudEngine(
        @Body data: EngineBodyPost
    ): Flowable<Response<DevApiResponse<EngineItem>>>

    @POST("users/v1/users/register")
    fun postRegister(
        @Body data: RegisterBodyPost
    ): Flowable<Response<DevApiResponse<ProfileItem>>>

    @POST("users/v1/users/logout")
    fun postLogout(
        @Body data: LogoutBodyPost
    ): Flowable<Response<JsonElement>>

    @POST("users/v1/users/forgot-account")
    fun postForgotAccountEmail(
        @Body data: ForgotAccountEmailBodyPost
    ): Flowable<Response<DevApiResponse<ForgotAccountEmailItem>>>

    @POST("users/v1/users/forgot-account")
    fun postForgotAccountPhoneNumber(
        @Body data: ForgotAccountPhoneNumberBodyPost
    ): Flowable<Response<DevApiResponse<ForgotAccountPhoneNumberItem>>>

    @POST("users/v1/users/otp")
    fun postGetOtpPhoneNumber(
        @Body data: OtpBodyPostPhoneNumber
    ): Flowable<Response<DevApiResponse<JsonElement>>>

    @POST("users/v1/users/otp")
    fun postGetOtpEmail(
        @Body data: OtpBodyPostEmail
    ): Flowable<Response<DevApiResponse<JsonElement>>>

    @POST("users/v1/users/verify-otp-code")
    fun postVerifyOtp(
        @Body data: VerificationBodyPost
    ): Flowable<Response<DevApiResponse<LoginItem>>>

    @POST("users/v1/users/verify-otp-code")
    fun postVerifyOtpEmail(
        @Body data: VerificationBodyPostEmail
    ): Flowable<Response<DevApiResponse<LoginItem>>>
}
