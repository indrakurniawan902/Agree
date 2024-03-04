package com.agree.ecosystem.users.presentation.menu.profile

import com.agree.ecosystem.users.domain.reqres.model.profile.Profile

interface ProfileDataContract {

    fun onGetProfileLoading()

    fun onGetProfileSuccess(data: Profile)

    fun onGetProfileFailed(e: Throwable?)
}
