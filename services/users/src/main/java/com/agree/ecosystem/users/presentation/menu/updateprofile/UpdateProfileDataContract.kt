package com.agree.ecosystem.users.presentation.menu.updateprofile

import com.agree.ecosystem.users.domain.reqres.model.profile.Profile

interface UpdateProfileDataContract {

    fun onLoading()

    fun onProfileLoading()

    fun onUpdateAvatarLoading()

    fun onGetProfileSuccess(data: Profile)

    fun onGetProfileFailed(e: Throwable?)

    fun doUpdateProfile()

    fun onUpdateProfileSuccess(data: Profile)

    fun onUpdateProfileFailed(e: Throwable?)

    fun onUpdateAvatarSuccess(data: Profile)

    fun onUpdateAvatarFailed(e: Throwable?)

    fun onSelectedZoneChanged(zoneType: String)
}
