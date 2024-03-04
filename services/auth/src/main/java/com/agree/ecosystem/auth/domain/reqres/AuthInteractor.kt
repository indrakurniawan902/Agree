package com.agree.ecosystem.auth.domain.reqres

import com.agree.ecosystem.auth.data.reqres.AuthRepository
import com.agree.ecosystem.auth.domain.reqres.model.forgotAccount.ForgotAccount
import com.agree.ecosystem.auth.domain.reqres.model.forgotAccount.ForgotAccountPhoneNumber
import com.agree.ecosystem.auth.domain.reqres.model.login.Engine
import com.agree.ecosystem.auth.domain.reqres.model.login.Login
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.google.gson.JsonElement
import io.reactivex.Flowable

class AuthInteractor(
    private val repo: AuthRepository
) : AuthUsecase {
    override fun login(username: String, password: String): Flowable<Pair<Login, Engine>> {
        return repo.login(username, password).map {
            it.first.toLogin() to it.second.toEngine()
        }
    }

    override fun register(
        name: String,
        username: String,
        phoneNumber: String,
        email: String?,
        password: String,
        confirmPassword: String
    ): Flowable<Profile> {
        return repo.register(name, username, phoneNumber, email, password, confirmPassword)
            .map { it.toProfile() }
    }

    override fun forgotAccountEmail(email: String): Flowable<ForgotAccount> {
        return repo.forgotAccountEmail(email).map { it.toForgotAccount() }
    }

    override fun forgotAccountPhoneNumber(phoneNumber: String): Flowable<ForgotAccountPhoneNumber> {
        return repo.forgotAccountPhoneNumber(phoneNumber).map { it.toForgotAccount() }
    }

    override fun getOtpPhoneNumber(phoneNumber: String): Flowable<JsonElement> {
        return repo.getOtpPhoneNumber(phoneNumber)
    }

    override fun getOtpEmail(email: String): Flowable<JsonElement> {
        return repo.getOtpEmail(email)
    }

    override fun verifyOption(
        phoneNumber: String,
        otp: String
    ): Flowable<Login> {
        return repo.postVerifyOption(phoneNumber, otp).map { it.toLogin() }
    }

    override fun verifyOptionEmail(email: String, otp: String): Flowable<Login> {
        return repo.postVerifyOptionEmail(email, otp).map { it.toLogin() }
    }

    override fun logout(): Flowable<JsonElement> {
        return repo.logout()
    }
}
