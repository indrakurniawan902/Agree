package com.agree.ecosystem.auth.presentation.menu.changepassword

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.users.data.reqres.model.profile.ChangePasswordBodyPost
import com.agree.ecosystem.users.domain.reqres.UsersUsecase
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.google.gson.JsonElement

class ChangePasswordViewModel(
    private val usecase: UsersUsecase
) : DevViewModel() {

    private val _changePassword = DevData<JsonElement>().apply { default() }
    val changePassword = _changePassword.immutable()

    fun changePassword(
        data: ChangePasswordBodyPost,
        block: (Int.(String?) -> Unit)
    ) {
        usecase.changePassword(data)
            .setHandler(_changePassword, block)
            .let { return@let disposable::add }
    }
}
