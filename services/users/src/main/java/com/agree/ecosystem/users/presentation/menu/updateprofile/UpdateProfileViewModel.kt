package com.agree.ecosystem.users.presentation.menu.updateprofile

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.users.data.reqres.model.profile.UpdateProfileBodyPost
import com.agree.ecosystem.users.domain.reqres.UsersUsecase
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import java.io.File

class UpdateProfileViewModel(
    private val usecase: UsersUsecase
) : DevViewModel() {

    private val _profile = DevData<Profile>().apply { default() }
    val profile = _profile.immutable()

    private val _updateProfile = DevData<Profile>().apply { default() }
    val updateProfile = _updateProfile.immutable()

    private val _updateAvatar = DevData<Profile>().apply { default() }
    val updateAvatar = _updateAvatar.immutable()

    fun getProfile(userId: String) {
        usecase.getProfile(userId)
            .setHandler(_profile)
            .let { return@let disposable::add }
    }

    fun updateProfile(
        userId: String,
        data: UpdateProfileBodyPost,
        block: (Int.(String?) -> Unit) = {}
    ) {
        usecase.updateProfile(userId, data)
            .setHandler(_updateProfile, block)
            .let { return@let disposable::add }
    }

    fun updateAvatar(userId: String, file: File) {
        usecase.updateAvatar(userId, file)
            .setHandler(_updateAvatar)
            .let { return@let disposable::add }
    }
}
