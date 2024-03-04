package com.agree.ecosystem.common.presentation.menu.settings

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.users.domain.reqres.UsersUsecase
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class SettingsViewModel(
    private val usersUsecase: UsersUsecase
) : DevViewModel() {

    private val _profile = DevData<Profile>().apply { default() }
    val profile = _profile.immutable()

    fun getProfile(userId: String) {
        usersUsecase.getProfile(userId)
            .setHandler(_profile)
            .let { return@let disposable::add }
    }
}
