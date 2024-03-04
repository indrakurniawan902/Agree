package com.agree.ecosystem.auth.data.reqres

import androidx.annotation.WorkerThread
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
import com.agree.ecosystem.auth.data.reqres.web.AgreeAuthApi
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.Constant
import com.agree.ecosystem.core.utils.utility.CredentialManagement
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileItem
import com.devbase.data.source.db.DbService
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.google.gson.JsonElement
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

@WorkerThread
class AuthDataStore(
    override val dbService: DbService? = null,
    override val webService: AgreeAuthApi,
    private val prefs: AgrPrefUsecase
) : AuthRepository {
    override fun login(username: String, password: String): Flowable<Pair<LoginItem, EngineItem>> {
        return Flowable.zip(
            webService.postLogin(LoginBodyPost(username, password, prefs.fcmToken))
                .lift(flowableApiError())
                .observeOn(Schedulers.io())
                .map { it.data },
            webService.postLoginCrudEngine(
                EngineBodyPost(
                    CredentialManagement.getUsernameEngine(),
                    CredentialManagement.getPasswordEngine(),
                    Constant.MONITORING_LOGIN_DURATION
                )
            ).lift(flowableApiError())
                .observeOn(Schedulers.io())
                .map { it.data }
        ) { login, engine ->
            login to engine
        }
    }

    override fun logout(): Flowable<JsonElement> {
        return webService.postLogout(LogoutBodyPost(prefs.fcmToken))
            .lift(flowableApiError())
    }

    override fun register(
        name: String,
        username: String,
        phoneNumber: String,
        email: String?,
        password: String,
        confirmPassword: String
    ): Flowable<ProfileItem> {
        return webService.postRegister(
            RegisterBodyPost(
                name,
                username,
                phoneNumber,
                email,
                password,
                confirmPassword
            )
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun forgotAccountEmail(email: String): Flowable<ForgotAccountEmailItem> {
        return webService.postForgotAccountEmail(
            ForgotAccountEmailBodyPost(
                email
            )
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun forgotAccountPhoneNumber(phoneNumber: String): Flowable<ForgotAccountPhoneNumberItem> {
        return webService.postForgotAccountPhoneNumber(
            ForgotAccountPhoneNumberBodyPost(
                phoneNumber
            )
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun getOtpPhoneNumber(phoneNumber: String): Flowable<JsonElement> {
        return webService.postGetOtpPhoneNumber(
            OtpBodyPostPhoneNumber(
                phoneNumber
            )
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun getOtpEmail(email: String): Flowable<JsonElement> {
        return webService.postGetOtpEmail(
            OtpBodyPostEmail(
                email
            )
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun postVerifyOption(
        phoneNumber: String,
        otp: String
    ): Flowable<LoginItem> {
        return webService.postVerifyOtp(
            VerificationBodyPost(
                phoneNumber, otp
            )
        ).lift(flowableApiError())
            .map { it.data }
    }

    override fun postVerifyOptionEmail(
        email: String,
        otp: String
    ): Flowable<LoginItem> {
        return webService.postVerifyOtpEmail(
            VerificationBodyPostEmail(
                email, otp
            )
        ).lift(flowableApiError())
            .map { it.data }
    }
}
