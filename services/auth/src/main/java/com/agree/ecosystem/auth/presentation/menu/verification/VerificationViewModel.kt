package com.agree.ecosystem.auth.presentation.menu.verification

import com.agree.ecosystem.auth.domain.reqres.AuthUsecase
import com.agree.ecosystem.auth.domain.reqres.model.login.Login
import com.agree.ecosystem.core.analytics.data.model.auth.LoginAnalyticsItem
import com.agree.ecosystem.core.analytics.domain.AuthAnalyticsUsecase
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class VerificationViewModel(
    private val usecase: AuthUsecase,
    private val authAnalytics: AuthAnalyticsUsecase
) : DevViewModel() {

    private val _verifyOption = DevData<Login>().apply {
        default()
    }

    val verifyOption = _verifyOption.immutable()

    fun doVerifyOption(
        phoneNumber: String,
        otp: String,
        block: (Int.(String?) -> Unit)
    ) {
        usecase.verifyOption(phoneNumber, otp)
            .setHandler(_verifyOption, block)
            .let { return@let disposable::add }
    }

    fun doVerifyOptionEmail(
        email: String,
        otp: String,
        block: (Int.(String?) -> Unit)
    ) {
        usecase.verifyOptionEmail(email, otp)
            .setHandler(_verifyOption, block)
            .let { return@let disposable::add }
    }

    fun trackLogin(
        data: LoginAnalyticsItem
    ) {
        authAnalytics.trackLogin(data)
    }
}
