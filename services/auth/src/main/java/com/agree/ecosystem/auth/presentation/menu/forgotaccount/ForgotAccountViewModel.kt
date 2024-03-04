package com.agree.ecosystem.auth.presentation.menu.forgotaccount

import com.agree.ecosystem.auth.domain.reqres.AuthUsecase
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.google.gson.JsonElement

class ForgotAccountViewModel(
    private val usecase: AuthUsecase
) : DevViewModel() {

    private val _forgotAccountEmail = DevData<JsonElement>().apply {
        default()
    }
    private val _forgotAccountPhoneNumber = DevData<JsonElement>().apply {
        default()
    }

    val forgotAccountEmail = _forgotAccountEmail.immutable()

    val forgotAccountPhoneNumber = _forgotAccountPhoneNumber.immutable()

    fun doForgotAccountEmail(
        email: String,
        block: (Int.(String?) -> Unit)
    ) {
        usecase.getOtpEmail(email)
            .setHandler(_forgotAccountEmail, block)
            .let { return@let disposable::add }
    }

    fun doForgotAccountPhoneNumber(
        phoneNumber: String,
        block: (Int.(String?) -> Unit)
    ) {
        usecase.getOtpPhoneNumber(phoneNumber)
            .setHandler(_forgotAccountPhoneNumber, block)
            .let { return@let disposable::add }
    }
}
