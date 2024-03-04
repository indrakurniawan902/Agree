package com.agree.ecosystem.auth.presentation.menu.verification

import com.agree.ecosystem.auth.domain.reqres.AuthUsecase
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.google.gson.JsonElement

class OtpViewModel(
    private val usecase: AuthUsecase
) : DevViewModel() {

    private val _getOtp = DevData<JsonElement>().apply {
        default()
    }

    val getOtp = _getOtp.immutable()

    fun doGetOtp(
        phoneNumber: String,
        block: (Int.(String?) -> Unit)
    ) {
        usecase.getOtpPhoneNumber(phoneNumber)
            .setHandler(_getOtp, block)
            .let { return@let disposable::add }
    }

    fun doGetOtpByEmail(
        email: String,
        block: (Int.(String?) -> Unit)
    ) {
        usecase.getOtpEmail(email)
            .setHandler(_getOtp, block)
            .let { return@let disposable::add }
    }
}
