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
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import com.google.gson.JsonElement
import io.reactivex.Flowable
import retrofit2.Response

class AgreeAuthApi(
    private val api: AgreeAuthApiClient
) : AgreeAuthApiClient, WebService {

    override fun postLogin(
        data: LoginBodyPost
    ): Flowable<Response<DevApiResponse<LoginItem>>> {
        return api.postLogin(data)
    }

    override fun postLoginCrudEngine(data: EngineBodyPost): Flowable<Response<DevApiResponse<EngineItem>>> {
        return api.postLoginCrudEngine(data)
    }

    override fun postRegister(data: RegisterBodyPost): Flowable<Response<DevApiResponse<ProfileItem>>> {
        return api.postRegister(data)
    }

    override fun postLogout(data: LogoutBodyPost): Flowable<Response<JsonElement>> {
        return api.postLogout(data)
    }

    override fun postForgotAccountEmail(data: ForgotAccountEmailBodyPost): Flowable<Response<DevApiResponse<ForgotAccountEmailItem>>> {
        return api.postForgotAccountEmail(data)
    }

    override fun postForgotAccountPhoneNumber(data: ForgotAccountPhoneNumberBodyPost): Flowable<Response<DevApiResponse<ForgotAccountPhoneNumberItem>>> {
        return api.postForgotAccountPhoneNumber(data)
    }

    override fun postGetOtpPhoneNumber(data: OtpBodyPostPhoneNumber): Flowable<Response<DevApiResponse<JsonElement>>> {
        return api.postGetOtpPhoneNumber(data)
    }

    override fun postGetOtpEmail(data: OtpBodyPostEmail): Flowable<Response<DevApiResponse<JsonElement>>> {
        return api.postGetOtpEmail(data)
    }

    override fun postVerifyOtp(data: VerificationBodyPost): Flowable<Response<DevApiResponse<LoginItem>>> {
        return api.postVerifyOtp(data)
    }

    override fun postVerifyOtpEmail(data: VerificationBodyPostEmail): Flowable<Response<DevApiResponse<LoginItem>>> {
        return api.postVerifyOtpEmail(data)
    }
}
