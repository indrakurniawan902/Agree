package com.agree.ecosystem.auth.presentation.menu.register

import com.agree.ecosystem.auth.domain.reqres.AuthUsecase
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class RegisterViewModel(
    private val usecase: AuthUsecase
) : DevViewModel() {

    private val _register = DevData<Profile>().apply {
        default()
    }

    val register = _register.immutable()

    fun doRegister(
        name: String,
        username: String,
        phoneNumber: String,
        email: String?,
        password: String,
        confirmPassword: String,
        block: (Int.(String?) -> Unit)
    ) {
        usecase.register(name, username, phoneNumber, email, password, confirmPassword)
            .setHandler(_register, block)
            .let { return@let disposable::add }
    }
}
