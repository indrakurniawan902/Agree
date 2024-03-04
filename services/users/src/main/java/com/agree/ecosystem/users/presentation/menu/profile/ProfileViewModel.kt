package com.agree.ecosystem.users.presentation.menu.profile

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.users.domain.reqres.UsersUsecase
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class ProfileViewModel(
    private val usecase: UsersUsecase
) : DevViewModel() {

    private val _profile = DevData<Profile>().apply { default() }
    val profile = _profile.immutable()

    fun getProfile(userId: String) {
        usecase.getProfile(userId)
            .setHandler(_profile)
            .let { return@let disposable::add }
    }
}
